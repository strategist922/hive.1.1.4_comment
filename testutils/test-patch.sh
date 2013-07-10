#!/usr/bin/env bash

#set -x
ulimit -n 1024

### Setup some variables.  
### JOB_NAME, SVN_REVISION, and BUILD_NUMBER are set by Hudson if it is run by patch process

###############################################################################
parseArgs() {
echo "args:$@"
      if [[ $# != 3 ]] ; then
        echo "ERROR: usage $0 <PATCH_DIR> <WORKSPACE_BASEDIR> <DEFECT>"
        cleanupAndExit 0
      fi
      PATCH_DIR=${1}
      BASEDIR=${2}
      defect=${3}
       ### Retrieve the defect number
      if [ "$defect" == '${defect}' ] ; then
        echo "Could not determine the patch to test.  Exiting."
        cleanupAndExit 0
      fi

      if [ ! -e "$PATCH_DIR" ] ; then
        mkdir -p $PATCH_DIR 
      fi
}

###############################################################################
checkout () {
  echo ""
  echo ""
  echo "======================================================================"
  echo "======================================================================"
  echo "    Testing patch for ${defect}."
  echo "======================================================================"
  echo "======================================================================"
  echo ""
  echo ""
  ### When run by a developer, if the workspace contains modifications, do not continue
  status=`svn stat`
    cd $BASEDIR
    svn revert -R .
    rm -rf `svn status`
    svn update
  return $?
}

###############################################################################
setup () {
  ### Download latest patch file (ignoring .htm and .html) when run from patch process
        VERSION=${SVN_REVISION}_${defect}_PATCH
	wget --save-cookies cookie.txt --spider --keep-session-cookies ${RBURL}${RBURI}/
	wget -q -O $PATCH_DIR/patch --load-cookies cookie.txt --post-data "username=${RBUSERNAME}&password=${RBPASSWORD}&next_page=${RBURI}/r/$defect/diff/raw/"  ${RBURL}${RBURI}/account/login/?next_page=${RBURI}/r/${defect}/diff/raw/
	rm cookie.txt

    JIRA_COMMENT="Here are the results of testing the latest attachment 
  $patchURL
  against trunk revision ${SVN_REVISION}."

}

###############################################################################
### Check for tests in the patch
checkTests () {
  echo ""
  echo ""
  echo "======================================================================"
  echo "======================================================================"
  echo "    Checking there are new or changed tests in the patch."
  echo "======================================================================"
  echo "======================================================================"
  echo ""
  echo ""
  testReferences=`grep -c -i '/test' $PATCH_DIR/patch`
  echo "There appear to be $testReferences test files referenced in the patch."
  if [[ $testReferences == 0 ]] ; then
    patchIsDoc=`grep -c -i 'title="documentation' $PATCH_DIR/jira`
    if [[ $patchIsDoc != 0 ]] ; then
      echo "The patch appears to be a documentation patch that doesn't require tests."
      JIRA_COMMENT="$JIRA_COMMENT

      +0 tests included.  The patch appears to be a documentation patch that doesn't require tests."
      return 0
    fi
    JIRA_COMMENT="$JIRA_COMMENT

    -1 tests included.  The patch doesn't appear to include any new or modified tests.
                        Please justify why no tests are needed for this patch."
    return 1
  fi
  JIRA_COMMENT="$JIRA_COMMENT

  %2b1 tests included.  The patch appears to include $testReferences new or modified tests."
  return 0
}

###############################################################################
### Attempt to apply the patch
applyPatch () {
  echo ""
  echo ""
  echo "======================================================================"
  echo "======================================================================"
  echo "    Applying patch."
  echo "======================================================================"
  echo "======================================================================"
  echo ""
  echo ""
	SVNURL=`svn info |grep URL`
	PROJECT=${SVNURL##*/}
	INDEX=`grep -m 1 Index $PATCH_DIR/patch`
	CONTAINS=`awk 'BEGIN {print split('"\"$INDEX\""', array, '"\"$PROJECT\""')}'`
	if [[ $CONTAINS -gt 1 ]] ; then
        	PREFIX=`awk 'BEGIN {split('"\"$INDEX\""', array, '"\"$PROJECT\""');print array[1]}'`
        	LEVEL=`awk 'BEGIN {print split('"\"$PREFIX\""', array, "/")}'`
	else
        	LEVEL=0
	fi
	  
  patch -E -p$LEVEL < $PATCH_DIR/patch
  if [[ $? != 0 ]] ; then
    echo "PATCH APPLICATION FAILED"
    JIRA_COMMENT="$JIRA_COMMENT

    -1 patch.  The patch command could not apply the patch."
    return 1
  fi
  return 0
}





###############################################################################
### Run the test-core target
runCoreTests () {
  echo ""
  echo ""
  echo "======================================================================"
  echo "======================================================================"
  echo "    Running core tests."
  echo "======================================================================"
  echo "======================================================================"
  echo ""
  echo ""
  
  ### Kill any rogue build processes from the last attempt
  $ps -auxwww | grep HadoopPatchProcess | /usr/bin/nawk '{print $2}' | /usr/bin/xargs -t -I {} /usr/bin/kill -9 {} > /dev/null

  echo "$ANT_HOME/bin/ant clean test"
  $ANT_HOME/bin/ant    clean test 
  if [[ $? != 0 ]] ; then
    JIRA_COMMENT="$JIRA_COMMENT

    -1 core tests.  The patch failed core unit tests."
    return 1
  fi
  JIRA_COMMENT="$JIRA_COMMENT

    %2b1 core tests.  The patch passed core unit tests."
  return 0
}


###############################################################################
### Submit a comment to the defect's Jira
submitJiraComment () {
  local result=$1
  if [[ $result == 0 ]] ; then
    comment="%2b1 overall.  $JIRA_COMMENT

$JIRA_COMMENT_FOOTER"
  else
    comment="-1 overall.  $JIRA_COMMENT

$JIRA_COMMENT_FOOTER"
  fi
  ### Output the test result to the console
  echo "



$comment"  

    echo ""
    echo ""
    echo "======================================================================"
    echo "======================================================================"
    echo "    Adding comment to Jira."
    echo "======================================================================"
    echo "======================================================================"
    echo ""
    echo ""

    ### Update Jira with a comment
#    export USER=hudson
#    $JIRACLI -s issues.apache.org/jira login hadoopqa $JIRA_PASSWD
#    $JIRACLI -s issues.apache.org/jira comment $defect "$comment"
#    $JIRACLI -s issues.apache.org/jira logout
	wget --user "$RBUSERNAME" --password "$RBPASSWORD" --post-data "username=$RBUSERNAME&password=$RBPASSWORD" --save-cookies cookie.txt --output-document json $RBURL$RBURI/api/json/accounts/login/
wget --user "$RBUSERNAME" --password "$RBPASSWORD" --load-cookies cookie.txt --post-data "body_top=$comment&shipit=0" --output-document json $RBURL$RBURI/api/json/reviewrequests/$defect/reviews/draft/publish/
wget --user "$RBUSERNAME" --password "$RBPASSWORD" --load-cookies cookie.txt --output-document json $RBURL$RBURI/api/json/accounts/logout/
rm cookie.txt
rm json
}

###############################################################################
### Cleanup files
cleanupAndExit () {
  local result=$1
    if [ -e "$PATCH_DIR" ] ; then
      cp -r $PATCH_DIR $BASEDIR
      rm -rf $PATCH_DIR
    fi
  echo ""
  echo ""
  echo "======================================================================"
  echo "======================================================================"
  echo "    Finished build."
  echo "======================================================================"
  echo "======================================================================"
  echo ""
  echo ""
  exit $result
}

###############################################################################
###############################################################################
###############################################################################
RBURL="http://rb.corp.taobao.com"
RBURI=""
RBUSERNAME="hudson"
RBPASSWORD="hudson"
HUDSONURL="http://10.232.29.21:8081/hudson/view/yunti"
JIRA_COMMENT=""
JIRA_COMMENT_FOOTER="Console output: $HUDSONURL/job/$JOB_NAME/$BUILD_NUMBER/console
This message is automatically generated."

### Check if arguments to the script have been specified properly or not
parseArgs $@
cd $BASEDIR

checkout
RESULT=$?
  if [[ $RESULT != 0 ]] ; then
    ### Resubmit build.
    $WGET -q -O $PATCH_DIR/build $TRIGGER_BUILD_URL
    exit 100
  fi
setup
RESULT=$?

checkTests
(( RESULT = RESULT + $? ))
applyPatch
if [[ $? != 0 ]] ; then
  submitJiraComment 1
  cleanupAndExit 1
fi
  ### DISABLE RELEASE AUDIT UNTIL HADOOP-4074 IS FIXED
  ### checkReleaseAuditWarnings
  ### (( RESULT = RESULT + $? ))
  runCoreTests
  (( RESULT = RESULT + $? ))
JIRA_COMMENT_FOOTER="Test results: $HUDSONURL/job/$JOB_NAME/$BUILD_NUMBER/testReport/
$JIRA_COMMENT_FOOTER"

submitJiraComment $RESULT
cleanupAndExit $RESULT


