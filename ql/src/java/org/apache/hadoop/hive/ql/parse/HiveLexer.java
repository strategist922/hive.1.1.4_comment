// $ANTLR 3.0.1 /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g 2013-07-11 12:43:26
package org.apache.hadoop.hive.ql.parse;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class HiveLexer extends Lexer {
    public static final int TOK_FUNCTIONDI=21;
    public static final int LSQUARE=344;
    public static final int KW_REPAIR=257;
    public static final int KW_FORMAT=269;
    public static final int KW_FIRST=222;
    public static final int TOK_OP_GT=29;
    public static final int TOK_ALTERTABLE_SERDEPROPERTIES=98;
    public static final int KW_DROP=206;
    public static final int IDLetter=407;
    public static final int KW_EXPLAIN=188;
    public static final int TOK_OP_GE=28;
    public static final int RPAREN=211;
    public static final int TOK_OP_ADD=31;
    public static final int TOK_TABCOLNAME=132;
    public static final int TOK_FUNCTIONSTAR=22;
    public static final int KW_THEN=338;
    public static final int TOK_FROM=9;
    public static final int TOK_TINYINT=67;
    public static final int DIVIDE=308;
    public static final int TOK_ALTERTABLE_LOCATION=102;
    public static final int TOK_ALTERTABLE_SERIALIZER=99;
    public static final int TOK_DESCFUNCTION=87;
    public static final int KW_FETCH=375;
    public static final int KW_FILEFORMAT=234;
    public static final int KW_MATERIALIZED=377;
    public static final int KW_TRANSFORM=306;
    public static final int TOK_PRIVCREATEUSER=178;
    public static final int TOK_LEFTSEMIJOIN=185;
    public static final int KW_UNIQUEJOIN=316;
    public static final int TOK_TBLSEQUENCEFILE=126;
    public static final int KW_SELECT=304;
    public static final int TOK_SELEXPR=8;
    public static final int TOK_UNIQUEJOIN=62;
    public static final int TOK_MAP=80;
    public static final int KW_BUCKET=325;
    public static final int KW_GROUP=329;
    public static final int KW_LOAD=190;
    public static final int KW_TO=216;
    public static final int KW_CHANGE=220;
    public static final int KW_DISTRIBUTE=333;
    public static final int KW_NOT=200;
    public static final int TOK_TMP_FILE=136;
    public static final int KW_ELSE=339;
    public static final int KW_INPATH=193;
    public static final int KW_OUT=326;
    public static final int TOK_STRUCT=79;
    public static final int KW_BOOLEAN=287;
    public static final int TOK_DOUBLE=73;
    public static final int KW_REGEXP=358;
    public static final int TOK_DATETIME=75;
    public static final int TOK_STORAGEHANDLER=130;
    public static final int HexDigit=404;
    public static final int KW_SORT=334;
    public static final int KW_FROM=254;
    public static final int TOK_MAPJOIN=156;
    public static final int TOK_TIMESTAMP=76;
    public static final int KW_REDUCE=313;
    public static final int TOK_IFNOTEXISTS=152;
    public static final int TOK_TBLTEXTFILE=127;
    public static final int KW_FOR=367;
    public static final int TOK_ALTERTABLE_FILEFORMAT=101;
    public static final int KW_READS=386;
    public static final int KW_SET=229;
    public static final int TOK_DROPUSER=162;
    public static final int PLUS=310;
    public static final int TOK_TABSORTCOLNAMEDESC=138;
    public static final int KW_EXTENDED=189;
    public static final int TOK_LOAD=63;
    public static final int TOK_TABALIAS=187;
    public static final int TOK_IFEXISTS=153;
    public static final int TOK_PRIV_GLOBAL=166;
    public static final int KW_LOCATION=227;
    public static final int TOK_PRIVALL=172;
    public static final int TOK_ALTERTABLE_TOUCH=95;
    public static final int KW_MSCK=256;
    public static final int TOK_TRANSFORM=45;
    public static final int LESSTHAN=295;
    public static final int KW_DELIMITED=270;
    public static final int TOK_PRIVALT=176;
    public static final int TOK_FUNCTION=20;
    public static final int KW_WHEN=337;
    public static final int TOK_TABLEROWFORMATLINES=125;
    public static final int TOK_CREATEFUNCTION=140;
    public static final int AMPERSAND=352;
    public static final int TOK_SHOWTABLES=107;
    public static final int MINUS=346;
    public static final int KW_FIELDS=272;
    public static final int Tokens=412;
    public static final int KW_SEQUENCEFILE=235;
    public static final int TOK_FALSE=44;
    public static final int COLON=282;
    public static final int TOK_TABLECOMMENT=118;
    public static final int TOK_LIKETABLE=85;
    public static final int TOK_SMALLINT=68;
    public static final int TOK_OP_LT=27;
    public static final int KW_TABLESAMPLE=324;
    public static final int TOK_TABLEPROPERTY=151;
    public static final int RCURLY=400;
    public static final int TOK_FULLOUTERJOIN=61;
    public static final int KW_USING=307;
    public static final int TOK_OP_LE=26;
    public static final int KW_NULL=343;
    public static final int TOK_OP_AND=39;
    public static final int TOK_OP_MOD=34;
    public static final int KW_SERDE=231;
    public static final int TOK_HINTARGLIST=158;
    public static final int KW_TINYINT=283;
    public static final int TOK_GROUPBY=51;
    public static final int TOK_CHARSETLITERAL=139;
    public static final int KW_CROSS=394;
    public static final int TOK_TABLEPARTCOLS=119;
    public static final int KW_COLLECTION=275;
    public static final int TOK_ALTERTABLE_DROPPARTS=94;
    public static final int KW_INSERT=301;
    public static final int TOK_SERDEPROPS=48;
    public static final int BITWISEXOR=349;
    public static final int TOK_OP_OR=40;
    public static final int TOK_DROPTABLE=115;
    public static final int TOK_TABLEROWFORMATMAPKEYS=124;
    public static final int Identifier=204;
    public static final int TOK_PARTVAL=12;
    public static final int TOK_OP_NE=25;
    public static final int TOK_TABLEBUCKETS=120;
    public static final int KW_RLIKE=357;
    public static final int KW_SCHEMAS=250;
    public static final int TOK_OP_NOT=41;
    public static final int IDDigit=408;
    public static final int COMMENT=411;
    public static final int TOK_PRIV_DB=167;
    public static final int TOK_ALIASLIST=50;
    public static final int KW_ESCAPED=274;
    public static final int KW_INT=285;
    public static final int KW_SMALLINT=284;
    public static final int TOK_INSERT=4;
    public static final int KW_IDENTIFIED=259;
    public static final int KW_TEXTFILE=236;
    public static final int TOK_USERSCRIPTCOLNAMES=159;
    public static final int KW_RENAME=215;
    public static final int TOK_UNIONTYPE=81;
    public static final int TOK_ALTERTABLE_REPLACECOLS=92;
    public static final int TOK_LATERAL_VIEW=186;
    public static final int KW_BINARY=393;
    public static final int TOK_STRING=77;
    public static final int KW_END=340;
    public static final int TOK_CLUSTERBY=54;
    public static final int TOK_FLOAT=72;
    public static final int TOK_SORTBY=56;
    public static final int KW_TABLES=251;
    public static final int TOK_USRLIST=164;
    public static final int Letter=403;
    public static final int KW_CURSOR=396;
    public static final int KW_TIMESTAMP=292;
    public static final int TOK_SELECTDI=7;
    public static final int KW_COLUMNS=219;
    public static final int KW_UNLOCK=381;
    public static final int KW_UNIONTYPE=298;
    public static final int KW_DESCRIBE=244;
    public static final int TOK_CREATETABLE=84;
    public static final int TOK_DROPDATABASE=113;
    public static final int KW_RCFILE=237;
    public static final int KW_CREATE=201;
    public static final int KW_MAPJOIN=311;
    public static final int TOK_DROPVIEW=143;
    public static final int TOK_PRIVGRANT=179;
    public static final int KW_WITH=232;
    public static final int TOK_ALTERTABLE_RENAMECOL=91;
    public static final int KW_GRANT=364;
    public static final int Number=264;
    public static final int COMMA=228;
    public static final int KW_WHILE=384;
    public static final int EQUAL=271;
    public static final int KW_UNARCHIVE=226;
    public static final int KW_RECORDREADER=266;
    public static final int TOK_ALLTABLEREF=165;
    public static final int TOK_DESTINATION=17;
    public static final int TOK_OP_BITAND=35;
    public static final int TOK_PRIVCRT=175;
    public static final int TOK_PRIVINS=174;
    public static final int TOK_HAVING=52;
    public static final int KW_UNION=299;
    public static final int KW_TEMPORARY=258;
    public static final int KW_CAST=335;
    public static final int KW_FALSE=362;
    public static final int KW_INTERSECT=376;
    public static final int TOK_EXPLAIN=145;
    public static final int TOK_ALTERTABLE_PARTITION=88;
    public static final int KW_STORED=280;
    public static final int KW_CASE=336;
    public static final int TOK_OP_BITNOT=36;
    public static final int TOK_TABCOLLIST=116;
    public static final int TOK_ALTERTABLE_CHANGECOL_AFTER_POSITION=104;
    public static final int TOK_TABTYPE=149;
    public static final int QUESTION=401;
    public static final int TOK_HINTLIST=154;
    public static final int TOK_SHOWGRANTS=109;
    public static final int KW_AS=212;
    public static final int KW_BEFORE=390;
    public static final int KW_KEY_TYPE=242;
    public static final int TOK_TABLELOCATION=133;
    public static final int TOK_RECORDREADER=183;
    public static final int KW_ALTER=213;
    public static final int TOK_PRIVSEL=173;
    public static final int TOK_TABREF=15;
    public static final int KW_LIKE=209;
    public static final int KW_PARTITIONED=261;
    public static final int STAR=309;
    public static final int KW_JOIN=317;
    public static final int TOK_ALTERTABLE_ARCHIVE=96;
    public static final int TOK_PRIV_TABLE=168;
    public static final int KW_PLUS=373;
    public static final int KW_ITEMS=276;
    public static final int MOD=350;
    public static final int TOK_OP_EQ=24;
    public static final int KW_ROW=268;
    public static final int KW_REVOKE=366;
    public static final int KW_FLOAT=288;
    public static final int KW_BOTH=392;
    public static final int EOF=-1;
    public static final int TOK_SHOWDATABASES=106;
    public static final int KW_ASC=281;
    public static final int TOK_DATABASECOMMENT=114;
    public static final int RegexComponent=409;
    public static final int KW_PARTITIONS=253;
    public static final int KW_RANGE=388;
    public static final int TOK_GRANT=169;
    public static final int TOK_TABLESAMPLE=135;
    public static final int TOK_PRIVDROP=177;
    public static final int TOK_QUERY=5;
    public static final int TOK_RIGHTOUTERJOIN=60;
    public static final int KW_TABLE=197;
    public static final int TOK_CREATEUSER=161;
    public static final int TOK_RECORDWRITER=184;
    public static final int KW_SCHEMA=203;
    public static final int KW_OPTION=365;
    public static final int TOK_WITHOPTS=181;
    public static final int TOK_COLTYPELIST=82;
    public static final int TOK_BIGINT=70;
    public static final int KW_ADD=217;
    public static final int TOK_ALTERTABLE_RENAME=89;
    public static final int KW_LATERAL=323;
    public static final int LCURLY=399;
    public static final int SEMICOLON=398;
    public static final int TOK_TABCOL=117;
    public static final int TOK_SETPASSWORD=182;
    public static final int KW_DELETE=372;
    public static final int TOK_WHERE=23;
    public static final int KW_TBLPROPERTIES=230;
    public static final int WS=410;
    public static final int TOK_TABLEROWFORMATFIELD=122;
    public static final int KW_REPLACE=218;
    public static final int KW_LOCK=380;
    public static final int KW_BY=260;
    public static final int TOK_UNION=57;
    public static final int TOK_SELECT=6;
    public static final int TOK_OP_LIKE=42;
    public static final int TOK_PRIVSUPER=180;
    public static final int KW_LOCAL=192;
    public static final int KW_SEMI=322;
    public static final int TOK_TABLEPROPLIST=148;
    public static final int KW_UTC=369;
    public static final int KW_LINES=279;
    public static final int TOK_SHOW_TABLESTATUS=111;
    public static final int KW_AND=359;
    public static final int TOK_SUBQUERY=16;
    public static final int CharSetName=341;
    public static final int KW_CLUSTERSTATUS=368;
    public static final int TOK_DROPFUNCTION=141;
    public static final int TOK_DESCTABLE=86;
    public static final int KW_DIRECTORY=302;
    public static final int LPAREN=210;
    public static final int KW_PARTITION=363;
    public static final int GREATERTHANOREQUALTO=356;
    public static final int KW_FORMATTED=246;
    public static final int KW_USE=205;
    public static final int KW_STRUCT=297;
    public static final int TOK_TRUE=43;
    public static final int KW_TERMINATED=273;
    public static final int TOK_CREATEVIEW=142;
    public static final int TOK_LOCAL_DIR=14;
    public static final int KW_IN=255;
    public static final int KW_INPUTFORMAT=238;
    public static final int KW_SSL=378;
    public static final int KW_OUTER=319;
    public static final int KW_IS=348;
    public static final int KW_IF=198;
    public static final int KW_DATABASES=249;
    public static final int TOK_ALTERVIEW_PROPERTIES=144;
    public static final int KW_ORDER=331;
    public static final int KW_ALL=300;
    public static final int KW_HAVING=330;
    public static final int TOK_ISNULL=65;
    public static final int TOK_ALLCOLREF=18;
    public static final int KW_FUNCTIONS=252;
    public static final int TOK_DIR=13;
    public static final int TOK_PRIVLIST=171;
    public static final int BITWISEOR=353;
    public static final int KW_SERDEPROPERTIES=233;
    public static final int StringLiteral=194;
    public static final int KW_ANALYZE=389;
    public static final int CharSetLiteral=342;
    public static final int TOK_TABLE_OR_COL=19;
    public static final int KW_PROCEDURE=382;
    public static final int TOK_ALTERTABLE_ADDPARTS=93;
    public static final int KW_CLUSTERED=262;
    public static final int KW_PURGE=387;
    public static final int KW_COMMENT=207;
    public static final int DIV=351;
    public static final int TOK_CREATEDATABASE=83;
    public static final int TOK_MSCK=105;
    public static final int KW_DATABASE=202;
    public static final int KW_RECORDWRITER=267;
    public static final int TOK_OP_BITXOR=38;
    public static final int KW_TOUCH=224;
    public static final int TOK_ALTERTABLE_ADDCOLS=90;
    public static final int KW_DATETIME=291;
    public static final int KW_STRING=293;
    public static final int KW_OUTPUTFORMAT=239;
    public static final int KW_LONG=371;
    public static final int TOK_NULL=64;
    public static final int KW_WHERE=328;
    public static final int KW_EXISTS=199;
    public static final int TOK_OP_DIV=30;
    public static final int NOTEQUAL=354;
    public static final int TOK_TABLE_PARTITION=100;
    public static final int TOK_DATE=74;
    public static final int TOK_TABLEROWFORMAT=121;
    public static final int KW_OVERWRITE=195;
    public static final int KW_DISTINCT=305;
    public static final int GREATERTHAN=296;
    public static final int TOK_SHOWPARTITIONS=110;
    public static final int TOK_ISNOTNULL=66;
    public static final int KW_FUNCTION=247;
    public static final int KW_PRESERVE=315;
    public static final int KW_CLUSTER=332;
    public static final int TOK_EXPLIST=49;
    public static final int TOK_USER=163;
    public static final int TOK_DISTRIBUTEBY=55;
    public static final int TOK_LIST=78;
    public static final int TOK_TBLRCFILE=128;
    public static final int TOK_ALTERTABLE_UNARCHIVE=97;
    public static final int TOK_HINT=155;
    public static final int TOK_TABLEPROPERTIES=147;
    public static final int KW_ARCHIVE=225;
    public static final int TOK_SERDE=46;
    public static final int KW_KEYS=278;
    public static final int KW_LEFT=318;
    public static final int TOK_SHOWFUNCTIONS=108;
    public static final int KW_VIEW=214;
    public static final int KW_DOUBLE=289;
    public static final int TOK_TABLESERIALIZER=146;
    public static final int TOK_LEFTOUTERJOIN=59;
    public static final int KW_SORTED=263;
    public static final int KW_MAP=277;
    public static final int KW_ELEM_TYPE=241;
    public static final int KW_FULL=321;
    public static final int TOK_SERDENAME=47;
    public static final int TOK_PARTITIONLOCATION=134;
    public static final int LESSTHANOREQUALTO=355;
    public static final int KW_ARRAY=294;
    public static final int KW_BUCKETS=265;
    public static final int DOLLAR=402;
    public static final int KW_UTCTIMESTAMP=370;
    public static final int KW_READ=385;
    public static final int TOK_OP_MUL=33;
    public static final int TOK_ALTERTABLE_CLUSTER_SORT=131;
    public static final int KW_DESC=245;
    public static final int Exponent=406;
    public static final int TOK_REVOKE=170;
    public static final int KW_TRUE=361;
    public static final int TOK_ALTERTABLE_PROPERTIES=103;
    public static final int KW_LIMIT=303;
    public static final int TOK_STREAMTABLE=157;
    public static final int KW_BIGINT=286;
    public static final int TOK_INT=69;
    public static final int KW_MINUS=374;
    public static final int TOK_TABLEFILEFORMAT=129;
    public static final int KW_RIGHT=320;
    public static final int TOK_ORDERBY=53;
    public static final int KW_EXTERNAL=208;
    public static final int KW_AFTER=223;
    public static final int TILDE=347;
    public static final int TOK_JOIN=58;
    public static final int KW_COLUMN=221;
    public static final int DOT=240;
    public static final int KW_UNDO=379;
    public static final int KW_STREAMTABLE=312;
    public static final int TOK_TAB=10;
    public static final int KW_TRIGGER=397;
    public static final int KW_CONTINUE=395;
    public static final int TOK_USERSCRIPTCOLSCHEMA=160;
    public static final int TOK_OP_BITOR=37;
    public static final int RSQUARE=345;
    public static final int TOK_PARTSPEC=11;
    public static final int TOK_SWITCHDATABASE=112;
    public static final int Digit=405;
    public static final int TOK_BOOLEAN=71;
    public static final int KW_UNSIGNED=383;
    public static final int KW_DATA=191;
    public static final int TOK_LIMIT=150;
    public static final int TOK_TABSORTCOLNAMEASC=137;
    public static final int KW_SHOW=248;
    public static final int KW_DATE=290;
    public static final int KW_INTO=196;
    public static final int KW_OR=360;
    public static final int TOK_TABLEROWFORMATCOLLITEMS=123;
    public static final int KW_ON=314;
    public static final int KW_VALUE_TYPE=243;
    public static final int KW_OF=327;
    public static final int KW_BETWEEN=391;
    public static final int TOK_OP_SUB=32;
    public HiveLexer() {;} 
    public HiveLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "/home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g"; }

    // $ANTLR start KW_TRUE
    public final void mKW_TRUE() throws RecognitionException {
        try {
            int _type = KW_TRUE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1740:9: ( 'TRUE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1740:11: 'TRUE'
            {
            match("TRUE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TRUE

    // $ANTLR start KW_FALSE
    public final void mKW_FALSE() throws RecognitionException {
        try {
            int _type = KW_FALSE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1741:10: ( 'FALSE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1741:12: 'FALSE'
            {
            match("FALSE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FALSE

    // $ANTLR start KW_ALL
    public final void mKW_ALL() throws RecognitionException {
        try {
            int _type = KW_ALL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1742:8: ( 'ALL' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1742:10: 'ALL'
            {
            match("ALL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ALL

    // $ANTLR start KW_AND
    public final void mKW_AND() throws RecognitionException {
        try {
            int _type = KW_AND;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1743:8: ( 'AND' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1743:10: 'AND'
            {
            match("AND"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_AND

    // $ANTLR start KW_OR
    public final void mKW_OR() throws RecognitionException {
        try {
            int _type = KW_OR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1744:7: ( 'OR' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1744:9: 'OR'
            {
            match("OR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_OR

    // $ANTLR start KW_NOT
    public final void mKW_NOT() throws RecognitionException {
        try {
            int _type = KW_NOT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1745:8: ( 'NOT' | '!' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='N') ) {
                alt1=1;
            }
            else if ( (LA1_0=='!') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1745:1: KW_NOT : ( 'NOT' | '!' );", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1745:10: 'NOT'
                    {
                    match("NOT"); 


                    }
                    break;
                case 2 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1745:18: '!'
                    {
                    match('!'); 

                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_NOT

    // $ANTLR start KW_LIKE
    public final void mKW_LIKE() throws RecognitionException {
        try {
            int _type = KW_LIKE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1746:9: ( 'LIKE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1746:11: 'LIKE'
            {
            match("LIKE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LIKE

    // $ANTLR start KW_IF
    public final void mKW_IF() throws RecognitionException {
        try {
            int _type = KW_IF;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1748:7: ( 'IF' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1748:9: 'IF'
            {
            match("IF"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_IF

    // $ANTLR start KW_EXISTS
    public final void mKW_EXISTS() throws RecognitionException {
        try {
            int _type = KW_EXISTS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1749:11: ( 'EXISTS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1749:13: 'EXISTS'
            {
            match("EXISTS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_EXISTS

    // $ANTLR start KW_ASC
    public final void mKW_ASC() throws RecognitionException {
        try {
            int _type = KW_ASC;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1751:8: ( 'ASC' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1751:10: 'ASC'
            {
            match("ASC"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ASC

    // $ANTLR start KW_DESC
    public final void mKW_DESC() throws RecognitionException {
        try {
            int _type = KW_DESC;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1752:9: ( 'DESC' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1752:11: 'DESC'
            {
            match("DESC"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DESC

    // $ANTLR start KW_ORDER
    public final void mKW_ORDER() throws RecognitionException {
        try {
            int _type = KW_ORDER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1753:10: ( 'ORDER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1753:12: 'ORDER'
            {
            match("ORDER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ORDER

    // $ANTLR start KW_BY
    public final void mKW_BY() throws RecognitionException {
        try {
            int _type = KW_BY;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1754:7: ( 'BY' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1754:9: 'BY'
            {
            match("BY"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BY

    // $ANTLR start KW_GROUP
    public final void mKW_GROUP() throws RecognitionException {
        try {
            int _type = KW_GROUP;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1755:10: ( 'GROUP' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1755:12: 'GROUP'
            {
            match("GROUP"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_GROUP

    // $ANTLR start KW_HAVING
    public final void mKW_HAVING() throws RecognitionException {
        try {
            int _type = KW_HAVING;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1756:11: ( 'HAVING' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1756:13: 'HAVING'
            {
            match("HAVING"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_HAVING

    // $ANTLR start KW_WHERE
    public final void mKW_WHERE() throws RecognitionException {
        try {
            int _type = KW_WHERE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1757:10: ( 'WHERE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1757:12: 'WHERE'
            {
            match("WHERE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_WHERE

    // $ANTLR start KW_FROM
    public final void mKW_FROM() throws RecognitionException {
        try {
            int _type = KW_FROM;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1758:9: ( 'FROM' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1758:11: 'FROM'
            {
            match("FROM"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FROM

    // $ANTLR start KW_AS
    public final void mKW_AS() throws RecognitionException {
        try {
            int _type = KW_AS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1759:7: ( 'AS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1759:9: 'AS'
            {
            match("AS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_AS

    // $ANTLR start KW_SELECT
    public final void mKW_SELECT() throws RecognitionException {
        try {
            int _type = KW_SELECT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1760:11: ( 'SELECT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1760:13: 'SELECT'
            {
            match("SELECT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SELECT

    // $ANTLR start KW_DISTINCT
    public final void mKW_DISTINCT() throws RecognitionException {
        try {
            int _type = KW_DISTINCT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1761:13: ( 'DISTINCT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1761:15: 'DISTINCT'
            {
            match("DISTINCT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DISTINCT

    // $ANTLR start KW_INSERT
    public final void mKW_INSERT() throws RecognitionException {
        try {
            int _type = KW_INSERT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1762:11: ( 'INSERT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1762:13: 'INSERT'
            {
            match("INSERT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_INSERT

    // $ANTLR start KW_OVERWRITE
    public final void mKW_OVERWRITE() throws RecognitionException {
        try {
            int _type = KW_OVERWRITE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1763:14: ( 'OVERWRITE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1763:16: 'OVERWRITE'
            {
            match("OVERWRITE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_OVERWRITE

    // $ANTLR start KW_OUTER
    public final void mKW_OUTER() throws RecognitionException {
        try {
            int _type = KW_OUTER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1764:10: ( 'OUTER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1764:12: 'OUTER'
            {
            match("OUTER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_OUTER

    // $ANTLR start KW_UNIQUEJOIN
    public final void mKW_UNIQUEJOIN() throws RecognitionException {
        try {
            int _type = KW_UNIQUEJOIN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1765:15: ( 'UNIQUEJOIN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1765:17: 'UNIQUEJOIN'
            {
            match("UNIQUEJOIN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UNIQUEJOIN

    // $ANTLR start KW_PRESERVE
    public final void mKW_PRESERVE() throws RecognitionException {
        try {
            int _type = KW_PRESERVE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1766:13: ( 'PRESERVE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1766:15: 'PRESERVE'
            {
            match("PRESERVE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_PRESERVE

    // $ANTLR start KW_JOIN
    public final void mKW_JOIN() throws RecognitionException {
        try {
            int _type = KW_JOIN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1767:9: ( 'JOIN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1767:11: 'JOIN'
            {
            match("JOIN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_JOIN

    // $ANTLR start KW_LEFT
    public final void mKW_LEFT() throws RecognitionException {
        try {
            int _type = KW_LEFT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1768:9: ( 'LEFT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1768:11: 'LEFT'
            {
            match("LEFT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LEFT

    // $ANTLR start KW_RIGHT
    public final void mKW_RIGHT() throws RecognitionException {
        try {
            int _type = KW_RIGHT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1769:10: ( 'RIGHT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1769:12: 'RIGHT'
            {
            match("RIGHT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_RIGHT

    // $ANTLR start KW_FULL
    public final void mKW_FULL() throws RecognitionException {
        try {
            int _type = KW_FULL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1770:9: ( 'FULL' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1770:11: 'FULL'
            {
            match("FULL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FULL

    // $ANTLR start KW_ON
    public final void mKW_ON() throws RecognitionException {
        try {
            int _type = KW_ON;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1771:7: ( 'ON' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1771:9: 'ON'
            {
            match("ON"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ON

    // $ANTLR start KW_PARTITION
    public final void mKW_PARTITION() throws RecognitionException {
        try {
            int _type = KW_PARTITION;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1772:14: ( 'PARTITION' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1772:16: 'PARTITION'
            {
            match("PARTITION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_PARTITION

    // $ANTLR start KW_PARTITIONS
    public final void mKW_PARTITIONS() throws RecognitionException {
        try {
            int _type = KW_PARTITIONS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1773:15: ( 'PARTITIONS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1773:17: 'PARTITIONS'
            {
            match("PARTITIONS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_PARTITIONS

    // $ANTLR start KW_TABLE
    public final void mKW_TABLE() throws RecognitionException {
        try {
            int _type = KW_TABLE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1774:9: ( 'TABLE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1774:11: 'TABLE'
            {
            match("TABLE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TABLE

    // $ANTLR start KW_TABLES
    public final void mKW_TABLES() throws RecognitionException {
        try {
            int _type = KW_TABLES;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1775:10: ( 'TABLES' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1775:12: 'TABLES'
            {
            match("TABLES"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TABLES

    // $ANTLR start KW_FUNCTIONS
    public final void mKW_FUNCTIONS() throws RecognitionException {
        try {
            int _type = KW_FUNCTIONS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1776:13: ( 'FUNCTIONS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1776:15: 'FUNCTIONS'
            {
            match("FUNCTIONS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FUNCTIONS

    // $ANTLR start KW_SHOW
    public final void mKW_SHOW() throws RecognitionException {
        try {
            int _type = KW_SHOW;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1777:8: ( 'SHOW' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1777:10: 'SHOW'
            {
            match("SHOW"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SHOW

    // $ANTLR start KW_MSCK
    public final void mKW_MSCK() throws RecognitionException {
        try {
            int _type = KW_MSCK;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1778:8: ( 'MSCK' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1778:10: 'MSCK'
            {
            match("MSCK"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_MSCK

    // $ANTLR start KW_REPAIR
    public final void mKW_REPAIR() throws RecognitionException {
        try {
            int _type = KW_REPAIR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1779:10: ( 'REPAIR' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1779:12: 'REPAIR'
            {
            match("REPAIR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_REPAIR

    // $ANTLR start KW_DIRECTORY
    public final void mKW_DIRECTORY() throws RecognitionException {
        try {
            int _type = KW_DIRECTORY;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1780:13: ( 'DIRECTORY' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1780:15: 'DIRECTORY'
            {
            match("DIRECTORY"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DIRECTORY

    // $ANTLR start KW_LOCAL
    public final void mKW_LOCAL() throws RecognitionException {
        try {
            int _type = KW_LOCAL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1781:9: ( 'LOCAL' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1781:11: 'LOCAL'
            {
            match("LOCAL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LOCAL

    // $ANTLR start KW_TRANSFORM
    public final void mKW_TRANSFORM() throws RecognitionException {
        try {
            int _type = KW_TRANSFORM;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1782:14: ( 'TRANSFORM' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1782:16: 'TRANSFORM'
            {
            match("TRANSFORM"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TRANSFORM

    // $ANTLR start KW_USING
    public final void mKW_USING() throws RecognitionException {
        try {
            int _type = KW_USING;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1783:9: ( 'USING' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1783:11: 'USING'
            {
            match("USING"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_USING

    // $ANTLR start KW_CLUSTER
    public final void mKW_CLUSTER() throws RecognitionException {
        try {
            int _type = KW_CLUSTER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1784:11: ( 'CLUSTER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1784:13: 'CLUSTER'
            {
            match("CLUSTER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CLUSTER

    // $ANTLR start KW_DISTRIBUTE
    public final void mKW_DISTRIBUTE() throws RecognitionException {
        try {
            int _type = KW_DISTRIBUTE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1785:14: ( 'DISTRIBUTE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1785:16: 'DISTRIBUTE'
            {
            match("DISTRIBUTE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DISTRIBUTE

    // $ANTLR start KW_SORT
    public final void mKW_SORT() throws RecognitionException {
        try {
            int _type = KW_SORT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1786:8: ( 'SORT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1786:10: 'SORT'
            {
            match("SORT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SORT

    // $ANTLR start KW_UNION
    public final void mKW_UNION() throws RecognitionException {
        try {
            int _type = KW_UNION;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1787:9: ( 'UNION' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1787:11: 'UNION'
            {
            match("UNION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UNION

    // $ANTLR start KW_LOAD
    public final void mKW_LOAD() throws RecognitionException {
        try {
            int _type = KW_LOAD;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1788:8: ( 'LOAD' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1788:10: 'LOAD'
            {
            match("LOAD"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LOAD

    // $ANTLR start KW_DATA
    public final void mKW_DATA() throws RecognitionException {
        try {
            int _type = KW_DATA;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1789:8: ( 'DATA' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1789:10: 'DATA'
            {
            match("DATA"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DATA

    // $ANTLR start KW_INPATH
    public final void mKW_INPATH() throws RecognitionException {
        try {
            int _type = KW_INPATH;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1790:10: ( 'INPATH' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1790:12: 'INPATH'
            {
            match("INPATH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_INPATH

    // $ANTLR start KW_IS
    public final void mKW_IS() throws RecognitionException {
        try {
            int _type = KW_IS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1791:6: ( 'IS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1791:8: 'IS'
            {
            match("IS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_IS

    // $ANTLR start KW_NULL
    public final void mKW_NULL() throws RecognitionException {
        try {
            int _type = KW_NULL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1792:8: ( 'NULL' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1792:10: 'NULL'
            {
            match("NULL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_NULL

    // $ANTLR start KW_CREATE
    public final void mKW_CREATE() throws RecognitionException {
        try {
            int _type = KW_CREATE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1793:10: ( 'CREATE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1793:12: 'CREATE'
            {
            match("CREATE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CREATE

    // $ANTLR start KW_EXTERNAL
    public final void mKW_EXTERNAL() throws RecognitionException {
        try {
            int _type = KW_EXTERNAL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1794:12: ( 'EXTERNAL' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1794:14: 'EXTERNAL'
            {
            match("EXTERNAL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_EXTERNAL

    // $ANTLR start KW_ALTER
    public final void mKW_ALTER() throws RecognitionException {
        try {
            int _type = KW_ALTER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1795:9: ( 'ALTER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1795:11: 'ALTER'
            {
            match("ALTER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ALTER

    // $ANTLR start KW_CHANGE
    public final void mKW_CHANGE() throws RecognitionException {
        try {
            int _type = KW_CHANGE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1796:10: ( 'CHANGE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1796:12: 'CHANGE'
            {
            match("CHANGE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CHANGE

    // $ANTLR start KW_COLUMN
    public final void mKW_COLUMN() throws RecognitionException {
        try {
            int _type = KW_COLUMN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1797:10: ( 'COLUMN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1797:12: 'COLUMN'
            {
            match("COLUMN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_COLUMN

    // $ANTLR start KW_FIRST
    public final void mKW_FIRST() throws RecognitionException {
        try {
            int _type = KW_FIRST;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1798:9: ( 'FIRST' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1798:11: 'FIRST'
            {
            match("FIRST"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FIRST

    // $ANTLR start KW_AFTER
    public final void mKW_AFTER() throws RecognitionException {
        try {
            int _type = KW_AFTER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1799:9: ( 'AFTER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1799:11: 'AFTER'
            {
            match("AFTER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_AFTER

    // $ANTLR start KW_DESCRIBE
    public final void mKW_DESCRIBE() throws RecognitionException {
        try {
            int _type = KW_DESCRIBE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1800:12: ( 'DESCRIBE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1800:14: 'DESCRIBE'
            {
            match("DESCRIBE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DESCRIBE

    // $ANTLR start KW_DROP
    public final void mKW_DROP() throws RecognitionException {
        try {
            int _type = KW_DROP;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1801:8: ( 'DROP' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1801:10: 'DROP'
            {
            match("DROP"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DROP

    // $ANTLR start KW_RENAME
    public final void mKW_RENAME() throws RecognitionException {
        try {
            int _type = KW_RENAME;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1802:10: ( 'RENAME' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1802:12: 'RENAME'
            {
            match("RENAME"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_RENAME

    // $ANTLR start KW_TO
    public final void mKW_TO() throws RecognitionException {
        try {
            int _type = KW_TO;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1803:6: ( 'TO' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1803:8: 'TO'
            {
            match("TO"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TO

    // $ANTLR start KW_COMMENT
    public final void mKW_COMMENT() throws RecognitionException {
        try {
            int _type = KW_COMMENT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1804:11: ( 'COMMENT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1804:13: 'COMMENT'
            {
            match("COMMENT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_COMMENT

    // $ANTLR start KW_BOOLEAN
    public final void mKW_BOOLEAN() throws RecognitionException {
        try {
            int _type = KW_BOOLEAN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1805:11: ( 'BOOLEAN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1805:13: 'BOOLEAN'
            {
            match("BOOLEAN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BOOLEAN

    // $ANTLR start KW_TINYINT
    public final void mKW_TINYINT() throws RecognitionException {
        try {
            int _type = KW_TINYINT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1806:11: ( 'TINYINT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1806:13: 'TINYINT'
            {
            match("TINYINT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TINYINT

    // $ANTLR start KW_SMALLINT
    public final void mKW_SMALLINT() throws RecognitionException {
        try {
            int _type = KW_SMALLINT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1807:12: ( 'SMALLINT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1807:14: 'SMALLINT'
            {
            match("SMALLINT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SMALLINT

    // $ANTLR start KW_INT
    public final void mKW_INT() throws RecognitionException {
        try {
            int _type = KW_INT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1808:7: ( 'INT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1808:9: 'INT'
            {
            match("INT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_INT

    // $ANTLR start KW_BIGINT
    public final void mKW_BIGINT() throws RecognitionException {
        try {
            int _type = KW_BIGINT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1809:10: ( 'BIGINT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1809:12: 'BIGINT'
            {
            match("BIGINT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BIGINT

    // $ANTLR start KW_FLOAT
    public final void mKW_FLOAT() throws RecognitionException {
        try {
            int _type = KW_FLOAT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1810:9: ( 'FLOAT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1810:11: 'FLOAT'
            {
            match("FLOAT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FLOAT

    // $ANTLR start KW_DOUBLE
    public final void mKW_DOUBLE() throws RecognitionException {
        try {
            int _type = KW_DOUBLE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1811:10: ( 'DOUBLE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1811:12: 'DOUBLE'
            {
            match("DOUBLE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DOUBLE

    // $ANTLR start KW_DATE
    public final void mKW_DATE() throws RecognitionException {
        try {
            int _type = KW_DATE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1812:8: ( 'DATE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1812:10: 'DATE'
            {
            match("DATE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DATE

    // $ANTLR start KW_DATETIME
    public final void mKW_DATETIME() throws RecognitionException {
        try {
            int _type = KW_DATETIME;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1813:12: ( 'DATETIME' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1813:14: 'DATETIME'
            {
            match("DATETIME"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DATETIME

    // $ANTLR start KW_TIMESTAMP
    public final void mKW_TIMESTAMP() throws RecognitionException {
        try {
            int _type = KW_TIMESTAMP;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1814:13: ( 'TIMESTAMP' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1814:15: 'TIMESTAMP'
            {
            match("TIMESTAMP"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TIMESTAMP

    // $ANTLR start KW_STRING
    public final void mKW_STRING() throws RecognitionException {
        try {
            int _type = KW_STRING;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1815:10: ( 'STRING' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1815:12: 'STRING'
            {
            match("STRING"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_STRING

    // $ANTLR start KW_ARRAY
    public final void mKW_ARRAY() throws RecognitionException {
        try {
            int _type = KW_ARRAY;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1816:9: ( 'ARRAY' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1816:11: 'ARRAY'
            {
            match("ARRAY"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ARRAY

    // $ANTLR start KW_STRUCT
    public final void mKW_STRUCT() throws RecognitionException {
        try {
            int _type = KW_STRUCT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1817:10: ( 'STRUCT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1817:12: 'STRUCT'
            {
            match("STRUCT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_STRUCT

    // $ANTLR start KW_MAP
    public final void mKW_MAP() throws RecognitionException {
        try {
            int _type = KW_MAP;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1818:7: ( 'MAP' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1818:9: 'MAP'
            {
            match("MAP"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_MAP

    // $ANTLR start KW_UNIONTYPE
    public final void mKW_UNIONTYPE() throws RecognitionException {
        try {
            int _type = KW_UNIONTYPE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1819:13: ( 'UNIONTYPE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1819:15: 'UNIONTYPE'
            {
            match("UNIONTYPE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UNIONTYPE

    // $ANTLR start KW_REDUCE
    public final void mKW_REDUCE() throws RecognitionException {
        try {
            int _type = KW_REDUCE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1820:10: ( 'REDUCE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1820:12: 'REDUCE'
            {
            match("REDUCE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_REDUCE

    // $ANTLR start KW_PARTITIONED
    public final void mKW_PARTITIONED() throws RecognitionException {
        try {
            int _type = KW_PARTITIONED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1821:15: ( 'PARTITIONED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1821:17: 'PARTITIONED'
            {
            match("PARTITIONED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_PARTITIONED

    // $ANTLR start KW_CLUSTERED
    public final void mKW_CLUSTERED() throws RecognitionException {
        try {
            int _type = KW_CLUSTERED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1822:13: ( 'CLUSTERED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1822:15: 'CLUSTERED'
            {
            match("CLUSTERED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CLUSTERED

    // $ANTLR start KW_SORTED
    public final void mKW_SORTED() throws RecognitionException {
        try {
            int _type = KW_SORTED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1823:10: ( 'SORTED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1823:12: 'SORTED'
            {
            match("SORTED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SORTED

    // $ANTLR start KW_INTO
    public final void mKW_INTO() throws RecognitionException {
        try {
            int _type = KW_INTO;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1824:8: ( 'INTO' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1824:10: 'INTO'
            {
            match("INTO"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_INTO

    // $ANTLR start KW_BUCKETS
    public final void mKW_BUCKETS() throws RecognitionException {
        try {
            int _type = KW_BUCKETS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1825:11: ( 'BUCKETS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1825:13: 'BUCKETS'
            {
            match("BUCKETS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BUCKETS

    // $ANTLR start KW_ROW
    public final void mKW_ROW() throws RecognitionException {
        try {
            int _type = KW_ROW;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1826:7: ( 'ROW' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1826:9: 'ROW'
            {
            match("ROW"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ROW

    // $ANTLR start KW_FORMAT
    public final void mKW_FORMAT() throws RecognitionException {
        try {
            int _type = KW_FORMAT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1827:10: ( 'FORMAT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1827:12: 'FORMAT'
            {
            match("FORMAT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FORMAT

    // $ANTLR start KW_DELIMITED
    public final void mKW_DELIMITED() throws RecognitionException {
        try {
            int _type = KW_DELIMITED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1828:13: ( 'DELIMITED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1828:15: 'DELIMITED'
            {
            match("DELIMITED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DELIMITED

    // $ANTLR start KW_FIELDS
    public final void mKW_FIELDS() throws RecognitionException {
        try {
            int _type = KW_FIELDS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1829:10: ( 'FIELDS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1829:12: 'FIELDS'
            {
            match("FIELDS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FIELDS

    // $ANTLR start KW_TERMINATED
    public final void mKW_TERMINATED() throws RecognitionException {
        try {
            int _type = KW_TERMINATED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1830:14: ( 'TERMINATED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1830:16: 'TERMINATED'
            {
            match("TERMINATED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TERMINATED

    // $ANTLR start KW_ESCAPED
    public final void mKW_ESCAPED() throws RecognitionException {
        try {
            int _type = KW_ESCAPED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1831:11: ( 'ESCAPED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1831:13: 'ESCAPED'
            {
            match("ESCAPED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ESCAPED

    // $ANTLR start KW_COLLECTION
    public final void mKW_COLLECTION() throws RecognitionException {
        try {
            int _type = KW_COLLECTION;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1832:14: ( 'COLLECTION' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1832:16: 'COLLECTION'
            {
            match("COLLECTION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_COLLECTION

    // $ANTLR start KW_ITEMS
    public final void mKW_ITEMS() throws RecognitionException {
        try {
            int _type = KW_ITEMS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1833:9: ( 'ITEMS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1833:11: 'ITEMS'
            {
            match("ITEMS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ITEMS

    // $ANTLR start KW_KEYS
    public final void mKW_KEYS() throws RecognitionException {
        try {
            int _type = KW_KEYS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1834:8: ( 'KEYS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1834:10: 'KEYS'
            {
            match("KEYS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_KEYS

    // $ANTLR start KW_KEY_TYPE
    public final void mKW_KEY_TYPE() throws RecognitionException {
        try {
            int _type = KW_KEY_TYPE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1835:12: ( '$KEY$' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1835:14: '$KEY$'
            {
            match("$KEY$"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_KEY_TYPE

    // $ANTLR start KW_LINES
    public final void mKW_LINES() throws RecognitionException {
        try {
            int _type = KW_LINES;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1836:9: ( 'LINES' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1836:11: 'LINES'
            {
            match("LINES"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LINES

    // $ANTLR start KW_STORED
    public final void mKW_STORED() throws RecognitionException {
        try {
            int _type = KW_STORED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1837:10: ( 'STORED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1837:12: 'STORED'
            {
            match("STORED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_STORED

    // $ANTLR start KW_FILEFORMAT
    public final void mKW_FILEFORMAT() throws RecognitionException {
        try {
            int _type = KW_FILEFORMAT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1838:14: ( 'FILEFORMAT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1838:16: 'FILEFORMAT'
            {
            match("FILEFORMAT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FILEFORMAT

    // $ANTLR start KW_SEQUENCEFILE
    public final void mKW_SEQUENCEFILE() throws RecognitionException {
        try {
            int _type = KW_SEQUENCEFILE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1839:16: ( 'SEQUENCEFILE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1839:18: 'SEQUENCEFILE'
            {
            match("SEQUENCEFILE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SEQUENCEFILE

    // $ANTLR start KW_TEXTFILE
    public final void mKW_TEXTFILE() throws RecognitionException {
        try {
            int _type = KW_TEXTFILE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1840:12: ( 'TEXTFILE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1840:14: 'TEXTFILE'
            {
            match("TEXTFILE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TEXTFILE

    // $ANTLR start KW_RCFILE
    public final void mKW_RCFILE() throws RecognitionException {
        try {
            int _type = KW_RCFILE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1841:10: ( 'RCFILE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1841:12: 'RCFILE'
            {
            match("RCFILE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_RCFILE

    // $ANTLR start KW_INPUTFORMAT
    public final void mKW_INPUTFORMAT() throws RecognitionException {
        try {
            int _type = KW_INPUTFORMAT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1842:15: ( 'INPUTFORMAT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1842:17: 'INPUTFORMAT'
            {
            match("INPUTFORMAT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_INPUTFORMAT

    // $ANTLR start KW_OUTPUTFORMAT
    public final void mKW_OUTPUTFORMAT() throws RecognitionException {
        try {
            int _type = KW_OUTPUTFORMAT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1843:16: ( 'OUTPUTFORMAT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1843:18: 'OUTPUTFORMAT'
            {
            match("OUTPUTFORMAT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_OUTPUTFORMAT

    // $ANTLR start KW_LOCATION
    public final void mKW_LOCATION() throws RecognitionException {
        try {
            int _type = KW_LOCATION;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1844:12: ( 'LOCATION' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1844:14: 'LOCATION'
            {
            match("LOCATION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LOCATION

    // $ANTLR start KW_TABLESAMPLE
    public final void mKW_TABLESAMPLE() throws RecognitionException {
        try {
            int _type = KW_TABLESAMPLE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1845:15: ( 'TABLESAMPLE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1845:17: 'TABLESAMPLE'
            {
            match("TABLESAMPLE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TABLESAMPLE

    // $ANTLR start KW_BUCKET
    public final void mKW_BUCKET() throws RecognitionException {
        try {
            int _type = KW_BUCKET;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1846:10: ( 'BUCKET' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1846:12: 'BUCKET'
            {
            match("BUCKET"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BUCKET

    // $ANTLR start KW_OUT
    public final void mKW_OUT() throws RecognitionException {
        try {
            int _type = KW_OUT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1847:7: ( 'OUT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1847:9: 'OUT'
            {
            match("OUT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_OUT

    // $ANTLR start KW_OF
    public final void mKW_OF() throws RecognitionException {
        try {
            int _type = KW_OF;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1848:6: ( 'OF' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1848:8: 'OF'
            {
            match("OF"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_OF

    // $ANTLR start KW_CAST
    public final void mKW_CAST() throws RecognitionException {
        try {
            int _type = KW_CAST;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1849:8: ( 'CAST' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1849:10: 'CAST'
            {
            match("CAST"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CAST

    // $ANTLR start KW_ADD
    public final void mKW_ADD() throws RecognitionException {
        try {
            int _type = KW_ADD;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1850:7: ( 'ADD' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1850:9: 'ADD'
            {
            match("ADD"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ADD

    // $ANTLR start KW_REPLACE
    public final void mKW_REPLACE() throws RecognitionException {
        try {
            int _type = KW_REPLACE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1851:11: ( 'REPLACE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1851:13: 'REPLACE'
            {
            match("REPLACE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_REPLACE

    // $ANTLR start KW_COLUMNS
    public final void mKW_COLUMNS() throws RecognitionException {
        try {
            int _type = KW_COLUMNS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1852:11: ( 'COLUMNS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1852:13: 'COLUMNS'
            {
            match("COLUMNS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_COLUMNS

    // $ANTLR start KW_RLIKE
    public final void mKW_RLIKE() throws RecognitionException {
        try {
            int _type = KW_RLIKE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1853:9: ( 'RLIKE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1853:11: 'RLIKE'
            {
            match("RLIKE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_RLIKE

    // $ANTLR start KW_REGEXP
    public final void mKW_REGEXP() throws RecognitionException {
        try {
            int _type = KW_REGEXP;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1854:10: ( 'REGEXP' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1854:12: 'REGEXP'
            {
            match("REGEXP"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_REGEXP

    // $ANTLR start KW_TEMPORARY
    public final void mKW_TEMPORARY() throws RecognitionException {
        try {
            int _type = KW_TEMPORARY;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1855:13: ( 'TEMPORARY' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1855:15: 'TEMPORARY'
            {
            match("TEMPORARY"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TEMPORARY

    // $ANTLR start KW_FUNCTION
    public final void mKW_FUNCTION() throws RecognitionException {
        try {
            int _type = KW_FUNCTION;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1856:12: ( 'FUNCTION' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1856:14: 'FUNCTION'
            {
            match("FUNCTION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FUNCTION

    // $ANTLR start KW_EXPLAIN
    public final void mKW_EXPLAIN() throws RecognitionException {
        try {
            int _type = KW_EXPLAIN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1857:11: ( 'EXPLAIN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1857:13: 'EXPLAIN'
            {
            match("EXPLAIN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_EXPLAIN

    // $ANTLR start KW_EXTENDED
    public final void mKW_EXTENDED() throws RecognitionException {
        try {
            int _type = KW_EXTENDED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1858:12: ( 'EXTENDED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1858:14: 'EXTENDED'
            {
            match("EXTENDED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_EXTENDED

    // $ANTLR start KW_FORMATTED
    public final void mKW_FORMATTED() throws RecognitionException {
        try {
            int _type = KW_FORMATTED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1859:13: ( 'FORMATTED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1859:15: 'FORMATTED'
            {
            match("FORMATTED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FORMATTED

    // $ANTLR start KW_SERDE
    public final void mKW_SERDE() throws RecognitionException {
        try {
            int _type = KW_SERDE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1860:9: ( 'SERDE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1860:11: 'SERDE'
            {
            match("SERDE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SERDE

    // $ANTLR start KW_WITH
    public final void mKW_WITH() throws RecognitionException {
        try {
            int _type = KW_WITH;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1861:8: ( 'WITH' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1861:10: 'WITH'
            {
            match("WITH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_WITH

    // $ANTLR start KW_SERDEPROPERTIES
    public final void mKW_SERDEPROPERTIES() throws RecognitionException {
        try {
            int _type = KW_SERDEPROPERTIES;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1862:19: ( 'SERDEPROPERTIES' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1862:21: 'SERDEPROPERTIES'
            {
            match("SERDEPROPERTIES"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SERDEPROPERTIES

    // $ANTLR start KW_LIMIT
    public final void mKW_LIMIT() throws RecognitionException {
        try {
            int _type = KW_LIMIT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1863:9: ( 'LIMIT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1863:11: 'LIMIT'
            {
            match("LIMIT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LIMIT

    // $ANTLR start KW_SET
    public final void mKW_SET() throws RecognitionException {
        try {
            int _type = KW_SET;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1864:7: ( 'SET' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1864:9: 'SET'
            {
            match("SET"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SET

    // $ANTLR start KW_TBLPROPERTIES
    public final void mKW_TBLPROPERTIES() throws RecognitionException {
        try {
            int _type = KW_TBLPROPERTIES;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1865:17: ( 'TBLPROPERTIES' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1865:19: 'TBLPROPERTIES'
            {
            match("TBLPROPERTIES"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TBLPROPERTIES

    // $ANTLR start KW_VALUE_TYPE
    public final void mKW_VALUE_TYPE() throws RecognitionException {
        try {
            int _type = KW_VALUE_TYPE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1866:14: ( '$VALUE$' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1866:16: '$VALUE$'
            {
            match("$VALUE$"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_VALUE_TYPE

    // $ANTLR start KW_ELEM_TYPE
    public final void mKW_ELEM_TYPE() throws RecognitionException {
        try {
            int _type = KW_ELEM_TYPE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1867:13: ( '$ELEM$' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1867:15: '$ELEM$'
            {
            match("$ELEM$"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ELEM_TYPE

    // $ANTLR start KW_CASE
    public final void mKW_CASE() throws RecognitionException {
        try {
            int _type = KW_CASE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1868:8: ( 'CASE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1868:10: 'CASE'
            {
            match("CASE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CASE

    // $ANTLR start KW_WHEN
    public final void mKW_WHEN() throws RecognitionException {
        try {
            int _type = KW_WHEN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1869:8: ( 'WHEN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1869:10: 'WHEN'
            {
            match("WHEN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_WHEN

    // $ANTLR start KW_THEN
    public final void mKW_THEN() throws RecognitionException {
        try {
            int _type = KW_THEN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1870:8: ( 'THEN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1870:10: 'THEN'
            {
            match("THEN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_THEN

    // $ANTLR start KW_ELSE
    public final void mKW_ELSE() throws RecognitionException {
        try {
            int _type = KW_ELSE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1871:8: ( 'ELSE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1871:10: 'ELSE'
            {
            match("ELSE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ELSE

    // $ANTLR start KW_END
    public final void mKW_END() throws RecognitionException {
        try {
            int _type = KW_END;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1872:7: ( 'END' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1872:9: 'END'
            {
            match("END"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_END

    // $ANTLR start KW_MAPJOIN
    public final void mKW_MAPJOIN() throws RecognitionException {
        try {
            int _type = KW_MAPJOIN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1873:11: ( 'MAPJOIN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1873:13: 'MAPJOIN'
            {
            match("MAPJOIN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_MAPJOIN

    // $ANTLR start KW_STREAMTABLE
    public final void mKW_STREAMTABLE() throws RecognitionException {
        try {
            int _type = KW_STREAMTABLE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1874:15: ( 'STREAMTABLE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1874:17: 'STREAMTABLE'
            {
            match("STREAMTABLE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_STREAMTABLE

    // $ANTLR start KW_CLUSTERSTATUS
    public final void mKW_CLUSTERSTATUS() throws RecognitionException {
        try {
            int _type = KW_CLUSTERSTATUS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1875:17: ( 'CLUSTERSTATUS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1875:19: 'CLUSTERSTATUS'
            {
            match("CLUSTERSTATUS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CLUSTERSTATUS

    // $ANTLR start KW_UTC
    public final void mKW_UTC() throws RecognitionException {
        try {
            int _type = KW_UTC;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1876:7: ( 'UTC' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1876:9: 'UTC'
            {
            match("UTC"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UTC

    // $ANTLR start KW_UTCTIMESTAMP
    public final void mKW_UTCTIMESTAMP() throws RecognitionException {
        try {
            int _type = KW_UTCTIMESTAMP;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1877:16: ( 'UTC_TMESTAMP' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1877:18: 'UTC_TMESTAMP'
            {
            match("UTC_TMESTAMP"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UTCTIMESTAMP

    // $ANTLR start KW_LONG
    public final void mKW_LONG() throws RecognitionException {
        try {
            int _type = KW_LONG;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1878:8: ( 'LONG' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1878:10: 'LONG'
            {
            match("LONG"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LONG

    // $ANTLR start KW_DELETE
    public final void mKW_DELETE() throws RecognitionException {
        try {
            int _type = KW_DELETE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1879:10: ( 'DELETE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1879:12: 'DELETE'
            {
            match("DELETE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DELETE

    // $ANTLR start KW_PLUS
    public final void mKW_PLUS() throws RecognitionException {
        try {
            int _type = KW_PLUS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1880:8: ( 'PLUS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1880:10: 'PLUS'
            {
            match("PLUS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_PLUS

    // $ANTLR start KW_MINUS
    public final void mKW_MINUS() throws RecognitionException {
        try {
            int _type = KW_MINUS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1881:9: ( 'MINUS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1881:11: 'MINUS'
            {
            match("MINUS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_MINUS

    // $ANTLR start KW_FETCH
    public final void mKW_FETCH() throws RecognitionException {
        try {
            int _type = KW_FETCH;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1882:9: ( 'FETCH' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1882:11: 'FETCH'
            {
            match("FETCH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FETCH

    // $ANTLR start KW_INTERSECT
    public final void mKW_INTERSECT() throws RecognitionException {
        try {
            int _type = KW_INTERSECT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1883:13: ( 'INTERSECT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1883:15: 'INTERSECT'
            {
            match("INTERSECT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_INTERSECT

    // $ANTLR start KW_VIEW
    public final void mKW_VIEW() throws RecognitionException {
        try {
            int _type = KW_VIEW;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1884:8: ( 'VIEW' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1884:10: 'VIEW'
            {
            match("VIEW"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_VIEW

    // $ANTLR start KW_IN
    public final void mKW_IN() throws RecognitionException {
        try {
            int _type = KW_IN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1885:6: ( 'IN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1885:8: 'IN'
            {
            match("IN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_IN

    // $ANTLR start KW_DATABASE
    public final void mKW_DATABASE() throws RecognitionException {
        try {
            int _type = KW_DATABASE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1886:12: ( 'DATABASE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1886:14: 'DATABASE'
            {
            match("DATABASE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DATABASE

    // $ANTLR start KW_DATABASES
    public final void mKW_DATABASES() throws RecognitionException {
        try {
            int _type = KW_DATABASES;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1887:13: ( 'DATABASES' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1887:15: 'DATABASES'
            {
            match("DATABASES"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_DATABASES

    // $ANTLR start KW_MATERIALIZED
    public final void mKW_MATERIALIZED() throws RecognitionException {
        try {
            int _type = KW_MATERIALIZED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1888:16: ( 'MATERIALIZED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1888:18: 'MATERIALIZED'
            {
            match("MATERIALIZED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_MATERIALIZED

    // $ANTLR start KW_SCHEMA
    public final void mKW_SCHEMA() throws RecognitionException {
        try {
            int _type = KW_SCHEMA;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1889:10: ( 'SCHEMA' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1889:12: 'SCHEMA'
            {
            match("SCHEMA"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SCHEMA

    // $ANTLR start KW_SCHEMAS
    public final void mKW_SCHEMAS() throws RecognitionException {
        try {
            int _type = KW_SCHEMAS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1890:11: ( 'SCHEMAS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1890:13: 'SCHEMAS'
            {
            match("SCHEMAS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SCHEMAS

    // $ANTLR start KW_GRANT
    public final void mKW_GRANT() throws RecognitionException {
        try {
            int _type = KW_GRANT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1891:9: ( 'GRANT' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1891:11: 'GRANT'
            {
            match("GRANT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_GRANT

    // $ANTLR start KW_REVOKE
    public final void mKW_REVOKE() throws RecognitionException {
        try {
            int _type = KW_REVOKE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1892:10: ( 'REVOKE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1892:12: 'REVOKE'
            {
            match("REVOKE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_REVOKE

    // $ANTLR start KW_SSL
    public final void mKW_SSL() throws RecognitionException {
        try {
            int _type = KW_SSL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1893:7: ( 'SSL' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1893:9: 'SSL'
            {
            match("SSL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SSL

    // $ANTLR start KW_UNDO
    public final void mKW_UNDO() throws RecognitionException {
        try {
            int _type = KW_UNDO;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1894:8: ( 'UNDO' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1894:10: 'UNDO'
            {
            match("UNDO"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UNDO

    // $ANTLR start KW_LOCK
    public final void mKW_LOCK() throws RecognitionException {
        try {
            int _type = KW_LOCK;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1895:8: ( 'LOCK' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1895:10: 'LOCK'
            {
            match("LOCK"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LOCK

    // $ANTLR start KW_UNLOCK
    public final void mKW_UNLOCK() throws RecognitionException {
        try {
            int _type = KW_UNLOCK;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1896:10: ( 'UNLOCK' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1896:12: 'UNLOCK'
            {
            match("UNLOCK"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UNLOCK

    // $ANTLR start KW_PROCEDURE
    public final void mKW_PROCEDURE() throws RecognitionException {
        try {
            int _type = KW_PROCEDURE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1897:13: ( 'PROCEDURE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1897:15: 'PROCEDURE'
            {
            match("PROCEDURE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_PROCEDURE

    // $ANTLR start KW_UNSIGNED
    public final void mKW_UNSIGNED() throws RecognitionException {
        try {
            int _type = KW_UNSIGNED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1898:12: ( 'UNSIGNED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1898:14: 'UNSIGNED'
            {
            match("UNSIGNED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UNSIGNED

    // $ANTLR start KW_WHILE
    public final void mKW_WHILE() throws RecognitionException {
        try {
            int _type = KW_WHILE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1899:9: ( 'WHILE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1899:11: 'WHILE'
            {
            match("WHILE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_WHILE

    // $ANTLR start KW_READ
    public final void mKW_READ() throws RecognitionException {
        try {
            int _type = KW_READ;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1900:8: ( 'READ' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1900:10: 'READ'
            {
            match("READ"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_READ

    // $ANTLR start KW_READS
    public final void mKW_READS() throws RecognitionException {
        try {
            int _type = KW_READS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1901:9: ( 'READS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1901:11: 'READS'
            {
            match("READS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_READS

    // $ANTLR start KW_PURGE
    public final void mKW_PURGE() throws RecognitionException {
        try {
            int _type = KW_PURGE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1902:9: ( 'PURGE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1902:11: 'PURGE'
            {
            match("PURGE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_PURGE

    // $ANTLR start KW_RANGE
    public final void mKW_RANGE() throws RecognitionException {
        try {
            int _type = KW_RANGE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1903:9: ( 'RANGE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1903:11: 'RANGE'
            {
            match("RANGE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_RANGE

    // $ANTLR start KW_ANALYZE
    public final void mKW_ANALYZE() throws RecognitionException {
        try {
            int _type = KW_ANALYZE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1904:11: ( 'ANALYZE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1904:13: 'ANALYZE'
            {
            match("ANALYZE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ANALYZE

    // $ANTLR start KW_BEFORE
    public final void mKW_BEFORE() throws RecognitionException {
        try {
            int _type = KW_BEFORE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1905:10: ( 'BEFORE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1905:12: 'BEFORE'
            {
            match("BEFORE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BEFORE

    // $ANTLR start KW_BETWEEN
    public final void mKW_BETWEEN() throws RecognitionException {
        try {
            int _type = KW_BETWEEN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1906:11: ( 'BETWEEN' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1906:13: 'BETWEEN'
            {
            match("BETWEEN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BETWEEN

    // $ANTLR start KW_BOTH
    public final void mKW_BOTH() throws RecognitionException {
        try {
            int _type = KW_BOTH;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1907:8: ( 'BOTH' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1907:10: 'BOTH'
            {
            match("BOTH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BOTH

    // $ANTLR start KW_BINARY
    public final void mKW_BINARY() throws RecognitionException {
        try {
            int _type = KW_BINARY;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1908:10: ( 'BINARY' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1908:12: 'BINARY'
            {
            match("BINARY"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_BINARY

    // $ANTLR start KW_CROSS
    public final void mKW_CROSS() throws RecognitionException {
        try {
            int _type = KW_CROSS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1909:9: ( 'CROSS' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1909:11: 'CROSS'
            {
            match("CROSS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CROSS

    // $ANTLR start KW_CONTINUE
    public final void mKW_CONTINUE() throws RecognitionException {
        try {
            int _type = KW_CONTINUE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1910:12: ( 'CONTINUE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1910:14: 'CONTINUE'
            {
            match("CONTINUE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CONTINUE

    // $ANTLR start KW_CURSOR
    public final void mKW_CURSOR() throws RecognitionException {
        try {
            int _type = KW_CURSOR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1911:10: ( 'CURSOR' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1911:12: 'CURSOR'
            {
            match("CURSOR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_CURSOR

    // $ANTLR start KW_TRIGGER
    public final void mKW_TRIGGER() throws RecognitionException {
        try {
            int _type = KW_TRIGGER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1912:11: ( 'TRIGGER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1912:13: 'TRIGGER'
            {
            match("TRIGGER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TRIGGER

    // $ANTLR start KW_RECORDREADER
    public final void mKW_RECORDREADER() throws RecognitionException {
        try {
            int _type = KW_RECORDREADER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1913:16: ( 'RECORDREADER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1913:18: 'RECORDREADER'
            {
            match("RECORDREADER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_RECORDREADER

    // $ANTLR start KW_RECORDWRITER
    public final void mKW_RECORDWRITER() throws RecognitionException {
        try {
            int _type = KW_RECORDWRITER;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1914:16: ( 'RECORDWRITER' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1914:18: 'RECORDWRITER'
            {
            match("RECORDWRITER"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_RECORDWRITER

    // $ANTLR start KW_SEMI
    public final void mKW_SEMI() throws RecognitionException {
        try {
            int _type = KW_SEMI;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1915:8: ( 'SEMI' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1915:10: 'SEMI'
            {
            match("SEMI"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_SEMI

    // $ANTLR start KW_LATERAL
    public final void mKW_LATERAL() throws RecognitionException {
        try {
            int _type = KW_LATERAL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1916:11: ( 'LATERAL' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1916:13: 'LATERAL'
            {
            match("LATERAL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_LATERAL

    // $ANTLR start KW_TOUCH
    public final void mKW_TOUCH() throws RecognitionException {
        try {
            int _type = KW_TOUCH;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1917:9: ( 'TOUCH' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1917:11: 'TOUCH'
            {
            match("TOUCH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_TOUCH

    // $ANTLR start KW_ARCHIVE
    public final void mKW_ARCHIVE() throws RecognitionException {
        try {
            int _type = KW_ARCHIVE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1918:11: ( 'ARCHIVE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1918:13: 'ARCHIVE'
            {
            match("ARCHIVE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_ARCHIVE

    // $ANTLR start KW_UNARCHIVE
    public final void mKW_UNARCHIVE() throws RecognitionException {
        try {
            int _type = KW_UNARCHIVE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1919:13: ( 'UNARCHIVE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1919:15: 'UNARCHIVE'
            {
            match("UNARCHIVE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_UNARCHIVE

    // $ANTLR start KW_USE
    public final void mKW_USE() throws RecognitionException {
        try {
            int _type = KW_USE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1920:7: ( 'USE' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1920:9: 'USE'
            {
            match("USE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_USE

    // $ANTLR start KW_IDENTIFIED
    public final void mKW_IDENTIFIED() throws RecognitionException {
        try {
            int _type = KW_IDENTIFIED;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1921:14: ( 'IDENTIFIED' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1921:16: 'IDENTIFIED'
            {
            match("IDENTIFIED"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_IDENTIFIED

    // $ANTLR start KW_OPTION
    public final void mKW_OPTION() throws RecognitionException {
        try {
            int _type = KW_OPTION;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1922:10: ( 'OPTION' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1922:12: 'OPTION'
            {
            match("OPTION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_OPTION

    // $ANTLR start KW_FOR
    public final void mKW_FOR() throws RecognitionException {
        try {
            int _type = KW_FOR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1923:7: ( 'FOR' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1923:9: 'FOR'
            {
            match("FOR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end KW_FOR

    // $ANTLR start DOT
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1928:5: ( '.' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1928:7: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DOT

    // $ANTLR start COLON
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1929:7: ( ':' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1929:9: ':'
            {
            match(':'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COLON

    // $ANTLR start COMMA
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1930:7: ( ',' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1930:9: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start SEMICOLON
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1931:11: ( ';' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1931:13: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SEMICOLON

    // $ANTLR start LPAREN
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1933:8: ( '(' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1933:10: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LPAREN

    // $ANTLR start RPAREN
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1934:8: ( ')' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1934:10: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RPAREN

    // $ANTLR start LSQUARE
    public final void mLSQUARE() throws RecognitionException {
        try {
            int _type = LSQUARE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1935:9: ( '[' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1935:11: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LSQUARE

    // $ANTLR start RSQUARE
    public final void mRSQUARE() throws RecognitionException {
        try {
            int _type = RSQUARE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1936:9: ( ']' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1936:11: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RSQUARE

    // $ANTLR start LCURLY
    public final void mLCURLY() throws RecognitionException {
        try {
            int _type = LCURLY;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1937:8: ( '{' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1937:10: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LCURLY

    // $ANTLR start RCURLY
    public final void mRCURLY() throws RecognitionException {
        try {
            int _type = RCURLY;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1938:8: ( '}' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1938:10: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RCURLY

    // $ANTLR start EQUAL
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1940:7: ( '=' | '==' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='=') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='=') ) {
                    alt2=2;
                }
                else {
                    alt2=1;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1940:1: EQUAL : ( '=' | '==' );", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1940:9: '='
                    {
                    match('='); 

                    }
                    break;
                case 2 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1940:15: '=='
                    {
                    match("=="); 


                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end EQUAL

    // $ANTLR start NOTEQUAL
    public final void mNOTEQUAL() throws RecognitionException {
        try {
            int _type = NOTEQUAL;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1941:10: ( '<>' | '!=' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='<') ) {
                alt3=1;
            }
            else if ( (LA3_0=='!') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1941:1: NOTEQUAL : ( '<>' | '!=' );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1941:12: '<>'
                    {
                    match("<>"); 


                    }
                    break;
                case 2 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1941:19: '!='
                    {
                    match("!="); 


                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NOTEQUAL

    // $ANTLR start LESSTHANOREQUALTO
    public final void mLESSTHANOREQUALTO() throws RecognitionException {
        try {
            int _type = LESSTHANOREQUALTO;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1942:19: ( '<=' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1942:21: '<='
            {
            match("<="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LESSTHANOREQUALTO

    // $ANTLR start LESSTHAN
    public final void mLESSTHAN() throws RecognitionException {
        try {
            int _type = LESSTHAN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1943:10: ( '<' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1943:12: '<'
            {
            match('<'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LESSTHAN

    // $ANTLR start GREATERTHANOREQUALTO
    public final void mGREATERTHANOREQUALTO() throws RecognitionException {
        try {
            int _type = GREATERTHANOREQUALTO;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1944:22: ( '>=' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1944:24: '>='
            {
            match(">="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end GREATERTHANOREQUALTO

    // $ANTLR start GREATERTHAN
    public final void mGREATERTHAN() throws RecognitionException {
        try {
            int _type = GREATERTHAN;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1945:13: ( '>' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1945:15: '>'
            {
            match('>'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end GREATERTHAN

    // $ANTLR start DIVIDE
    public final void mDIVIDE() throws RecognitionException {
        try {
            int _type = DIVIDE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1947:8: ( '/' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1947:10: '/'
            {
            match('/'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DIVIDE

    // $ANTLR start PLUS
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1948:6: ( '+' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1948:8: '+'
            {
            match('+'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end PLUS

    // $ANTLR start MINUS
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1949:7: ( '-' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1949:9: '-'
            {
            match('-'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end MINUS

    // $ANTLR start STAR
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1950:6: ( '*' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1950:8: '*'
            {
            match('*'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end STAR

    // $ANTLR start MOD
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1951:5: ( '%' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1951:7: '%'
            {
            match('%'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end MOD

    // $ANTLR start DIV
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1952:5: ( 'DIV' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1952:7: 'DIV'
            {
            match("DIV"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DIV

    // $ANTLR start AMPERSAND
    public final void mAMPERSAND() throws RecognitionException {
        try {
            int _type = AMPERSAND;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1954:11: ( '&' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1954:13: '&'
            {
            match('&'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end AMPERSAND

    // $ANTLR start TILDE
    public final void mTILDE() throws RecognitionException {
        try {
            int _type = TILDE;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1955:7: ( '~' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1955:9: '~'
            {
            match('~'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end TILDE

    // $ANTLR start BITWISEOR
    public final void mBITWISEOR() throws RecognitionException {
        try {
            int _type = BITWISEOR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1956:11: ( '|' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1956:13: '|'
            {
            match('|'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end BITWISEOR

    // $ANTLR start BITWISEXOR
    public final void mBITWISEXOR() throws RecognitionException {
        try {
            int _type = BITWISEXOR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1957:12: ( '^' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1957:14: '^'
            {
            match('^'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end BITWISEXOR

    // $ANTLR start QUESTION
    public final void mQUESTION() throws RecognitionException {
        try {
            int _type = QUESTION;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1958:10: ( '?' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1958:12: '?'
            {
            match('?'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end QUESTION

    // $ANTLR start DOLLAR
    public final void mDOLLAR() throws RecognitionException {
        try {
            int _type = DOLLAR;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1959:8: ( '$' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1959:10: '$'
            {
            match('$'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DOLLAR

    // $ANTLR start Letter
    public final void mLetter() throws RecognitionException {
        try {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1964:5: ( 'a' .. 'z' | 'A' .. 'Z' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end Letter

    // $ANTLR start HexDigit
    public final void mHexDigit() throws RecognitionException {
        try {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1969:5: ( 'a' .. 'f' | 'A' .. 'F' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end HexDigit

    // $ANTLR start Digit
    public final void mDigit() throws RecognitionException {
        try {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1974:5: ( '0' .. '9' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1975:5: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end Digit

    // $ANTLR start Exponent
    public final void mExponent() throws RecognitionException {
        try {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1980:5: ( 'e' ( PLUS | MINUS )? ( Digit )+ )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1981:5: 'e' ( PLUS | MINUS )? ( Digit )+
            {
            match('e'); 
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1981:9: ( PLUS | MINUS )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='+'||LA4_0=='-') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }


                    }
                    break;

            }

            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1981:25: ( Digit )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1981:26: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end Exponent

    // $ANTLR start StringLiteral
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1985:5: ( ( '\\'' (~ ( '\\'' | '\\\\' ) | ( '\\\\' . ) )* '\\'' | '\\\"' (~ ( '\\\"' | '\\\\' ) | ( '\\\\' . ) )* '\\\"' )+ )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:5: ( '\\'' (~ ( '\\'' | '\\\\' ) | ( '\\\\' . ) )* '\\'' | '\\\"' (~ ( '\\\"' | '\\\\' ) | ( '\\\\' . ) )* '\\\"' )+
            {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:5: ( '\\'' (~ ( '\\'' | '\\\\' ) | ( '\\\\' . ) )* '\\'' | '\\\"' (~ ( '\\\"' | '\\\\' ) | ( '\\\\' . ) )* '\\\"' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=3;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='\'') ) {
                    alt8=1;
                }
                else if ( (LA8_0=='\"') ) {
                    alt8=2;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:7: '\\'' (~ ( '\\'' | '\\\\' ) | ( '\\\\' . ) )* '\\''
            	    {
            	    match('\''); 
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:12: (~ ( '\\'' | '\\\\' ) | ( '\\\\' . ) )*
            	    loop6:
            	    do {
            	        int alt6=3;
            	        int LA6_0 = input.LA(1);

            	        if ( ((LA6_0>='\u0000' && LA6_0<='&')||(LA6_0>='(' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFE')) ) {
            	            alt6=1;
            	        }
            	        else if ( (LA6_0=='\\') ) {
            	            alt6=2;
            	        }


            	        switch (alt6) {
            	    	case 1 :
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:14: ~ ( '\\'' | '\\\\' )
            	    	    {
            	    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
            	    	        input.consume();

            	    	    }
            	    	    else {
            	    	        MismatchedSetException mse =
            	    	            new MismatchedSetException(null,input);
            	    	        recover(mse);    throw mse;
            	    	    }


            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:29: ( '\\\\' . )
            	    	    {
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:29: ( '\\\\' . )
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1986:30: '\\\\' .
            	    	    {
            	    	    match('\\'); 
            	    	    matchAny(); 

            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop6;
            	        }
            	    } while (true);

            	    match('\''); 

            	    }
            	    break;
            	case 2 :
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1987:7: '\\\"' (~ ( '\\\"' | '\\\\' ) | ( '\\\\' . ) )* '\\\"'
            	    {
            	    match('\"'); 
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1987:12: (~ ( '\\\"' | '\\\\' ) | ( '\\\\' . ) )*
            	    loop7:
            	    do {
            	        int alt7=3;
            	        int LA7_0 = input.LA(1);

            	        if ( ((LA7_0>='\u0000' && LA7_0<='!')||(LA7_0>='#' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFE')) ) {
            	            alt7=1;
            	        }
            	        else if ( (LA7_0=='\\') ) {
            	            alt7=2;
            	        }


            	        switch (alt7) {
            	    	case 1 :
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1987:14: ~ ( '\\\"' | '\\\\' )
            	    	    {
            	    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
            	    	        input.consume();

            	    	    }
            	    	    else {
            	    	        MismatchedSetException mse =
            	    	            new MismatchedSetException(null,input);
            	    	        recover(mse);    throw mse;
            	    	    }


            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1987:29: ( '\\\\' . )
            	    	    {
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1987:29: ( '\\\\' . )
            	    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1987:30: '\\\\' .
            	    	    {
            	    	    match('\\'); 
            	    	    matchAny(); 

            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop7;
            	        }
            	    } while (true);

            	    match('\"'); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end StringLiteral

    // $ANTLR start CharSetLiteral
    public final void mCharSetLiteral() throws RecognitionException {
        try {
            int _type = CharSetLiteral;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1992:5: ( StringLiteral | '0' 'X' ( HexDigit | Digit )+ )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\"'||LA10_0=='\'') ) {
                alt10=1;
            }
            else if ( (LA10_0=='0') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1991:1: CharSetLiteral : ( StringLiteral | '0' 'X' ( HexDigit | Digit )+ );", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1993:5: StringLiteral
                    {
                    mStringLiteral(); 

                    }
                    break;
                case 2 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1994:7: '0' 'X' ( HexDigit | Digit )+
                    {
                    match('0'); 
                    match('X'); 
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1994:15: ( HexDigit | Digit )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='F')||(LA9_0>='a' && LA9_0<='f')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
                    	    {
                    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);


                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end CharSetLiteral

    // $ANTLR start Number
    public final void mNumber() throws RecognitionException {
        try {
            int _type = Number;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1998:5: ( ( Digit )+ ( DOT ( Digit )* ( Exponent )? | Exponent )? )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:5: ( Digit )+ ( DOT ( Digit )* ( Exponent )? | Exponent )?
            {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:5: ( Digit )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:6: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);

            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:14: ( DOT ( Digit )* ( Exponent )? | Exponent )?
            int alt14=3;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='.') ) {
                alt14=1;
            }
            else if ( (LA14_0=='e') ) {
                alt14=2;
            }
            switch (alt14) {
                case 1 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:16: DOT ( Digit )* ( Exponent )?
                    {
                    mDOT(); 
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:20: ( Digit )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:21: Digit
                    	    {
                    	    mDigit(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);

                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:29: ( Exponent )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='e') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:30: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1999:43: Exponent
                    {
                    mExponent(); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end Number

    // $ANTLR start IDLetter
    public final void mIDLetter() throws RecognitionException {
        try {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2005:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end IDLetter

    // $ANTLR start IDDigit
    public final void mIDDigit() throws RecognitionException {
        try {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2022:5: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='\u0660' && input.LA(1)<='\u0669')||(input.LA(1)>='\u06F0' && input.LA(1)<='\u06F9')||(input.LA(1)>='\u0966' && input.LA(1)<='\u096F')||(input.LA(1)>='\u09E6' && input.LA(1)<='\u09EF')||(input.LA(1)>='\u0A66' && input.LA(1)<='\u0A6F')||(input.LA(1)>='\u0AE6' && input.LA(1)<='\u0AEF')||(input.LA(1)>='\u0B66' && input.LA(1)<='\u0B6F')||(input.LA(1)>='\u0BE7' && input.LA(1)<='\u0BEF')||(input.LA(1)>='\u0C66' && input.LA(1)<='\u0C6F')||(input.LA(1)>='\u0CE6' && input.LA(1)<='\u0CEF')||(input.LA(1)>='\u0D66' && input.LA(1)<='\u0D6F')||(input.LA(1)>='\u0E50' && input.LA(1)<='\u0E59')||(input.LA(1)>='\u0ED0' && input.LA(1)<='\u0ED9')||(input.LA(1)>='\u1040' && input.LA(1)<='\u1049') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end IDDigit

    // $ANTLR start RegexComponent
    public final void mRegexComponent() throws RecognitionException {
        try {
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2041:5: ( IDLetter | IDDigit | PLUS | STAR | QUESTION | MINUS | DOT | LPAREN | RPAREN | LSQUARE | RSQUARE | LCURLY | RCURLY | BITWISEXOR | BITWISEOR | DOLLAR )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='(' && input.LA(1)<='+')||(input.LA(1)>='-' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<='9')||input.LA(1)=='?'||(input.LA(1)>='A' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='}')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end RegexComponent

    // $ANTLR start Identifier
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2048:5: ( IDLetter ( IDLetter | IDDigit )* | '`' ( RegexComponent )+ '`' )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='$'||(LA17_0>='A' && LA17_0<='Z')||LA17_0=='_'||(LA17_0>='a' && LA17_0<='z')||(LA17_0>='\u00C0' && LA17_0<='\u00D6')||(LA17_0>='\u00D8' && LA17_0<='\u00F6')||(LA17_0>='\u00F8' && LA17_0<='\u1FFF')||(LA17_0>='\u3040' && LA17_0<='\u318F')||(LA17_0>='\u3300' && LA17_0<='\u337F')||(LA17_0>='\u3400' && LA17_0<='\u3D2D')||(LA17_0>='\u4E00' && LA17_0<='\u9FFF')||(LA17_0>='\uF900' && LA17_0<='\uFAFF')) ) {
                alt17=1;
            }
            else if ( (LA17_0=='`') ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2047:1: Identifier : ( IDLetter ( IDLetter | IDDigit )* | '`' ( RegexComponent )+ '`' );", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2049:5: IDLetter ( IDLetter | IDDigit )*
                    {
                    mIDLetter(); 
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2049:14: ( IDLetter | IDDigit )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0=='$'||(LA15_0>='0' && LA15_0<='9')||(LA15_0>='A' && LA15_0<='Z')||LA15_0=='_'||(LA15_0>='a' && LA15_0<='z')||(LA15_0>='\u00C0' && LA15_0<='\u00D6')||(LA15_0>='\u00D8' && LA15_0<='\u00F6')||(LA15_0>='\u00F8' && LA15_0<='\u1FFF')||(LA15_0>='\u3040' && LA15_0<='\u318F')||(LA15_0>='\u3300' && LA15_0<='\u337F')||(LA15_0>='\u3400' && LA15_0<='\u3D2D')||(LA15_0>='\u4E00' && LA15_0<='\u9FFF')||(LA15_0>='\uF900' && LA15_0<='\uFAFF')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
                    	    {
                    	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2050:7: '`' ( RegexComponent )+ '`'
                    {
                    match('`'); 
                    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2050:11: ( RegexComponent )+
                    int cnt16=0;
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0=='$'||(LA16_0>='(' && LA16_0<='+')||(LA16_0>='-' && LA16_0<='.')||(LA16_0>='0' && LA16_0<='9')||LA16_0=='?'||(LA16_0>='A' && LA16_0<='[')||(LA16_0>=']' && LA16_0<='_')||(LA16_0>='a' && LA16_0<='}')||(LA16_0>='\u00C0' && LA16_0<='\u00D6')||(LA16_0>='\u00D8' && LA16_0<='\u00F6')||(LA16_0>='\u00F8' && LA16_0<='\u1FFF')||(LA16_0>='\u3040' && LA16_0<='\u318F')||(LA16_0>='\u3300' && LA16_0<='\u337F')||(LA16_0>='\u3400' && LA16_0<='\u3D2D')||(LA16_0>='\u4E00' && LA16_0<='\u9FFF')||(LA16_0>='\uF900' && LA16_0<='\uFAFF')) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2050:11: RegexComponent
                    	    {
                    	    mRegexComponent(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt16 >= 1 ) break loop16;
                                EarlyExitException eee =
                                    new EarlyExitException(16, input);
                                throw eee;
                        }
                        cnt16++;
                    } while (true);

                    match('`'); 

                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end Identifier

    // $ANTLR start CharSetName
    public final void mCharSetName() throws RecognitionException {
        try {
            int _type = CharSetName;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2054:5: ( '_' ( Letter | Digit | '_' | '-' | '.' | ':' )+ )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2055:5: '_' ( Letter | Digit | '_' | '-' | '.' | ':' )+
            {
            match('_'); 
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2055:9: ( Letter | Digit | '_' | '-' | '.' | ':' )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='-' && LA18_0<='.')||(LA18_0>='0' && LA18_0<=':')||(LA18_0>='A' && LA18_0<='Z')||LA18_0=='_'||(LA18_0>='a' && LA18_0<='z')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:
            	    {
            	    if ( (input.LA(1)>='-' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<=':')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end CharSetName

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2058:5: ( ( ' ' | '\\r' | '\\t' | '\\n' ) )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2058:8: ( ' ' | '\\r' | '\\t' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2062:3: ( '--' (~ ( '\\n' | '\\r' ) )* )
            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2062:5: '--' (~ ( '\\n' | '\\r' ) )*
            {
            match("--"); 

            // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2062:10: (~ ( '\\n' | '\\r' ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='\u0000' && LA19_0<='\t')||(LA19_0>='\u000B' && LA19_0<='\f')||(LA19_0>='\u000E' && LA19_0<='\uFFFE')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:2062:11: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             channel=HIDDEN; 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMMENT

    public void mTokens() throws RecognitionException {
        // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:8: ( KW_TRUE | KW_FALSE | KW_ALL | KW_AND | KW_OR | KW_NOT | KW_LIKE | KW_IF | KW_EXISTS | KW_ASC | KW_DESC | KW_ORDER | KW_BY | KW_GROUP | KW_HAVING | KW_WHERE | KW_FROM | KW_AS | KW_SELECT | KW_DISTINCT | KW_INSERT | KW_OVERWRITE | KW_OUTER | KW_UNIQUEJOIN | KW_PRESERVE | KW_JOIN | KW_LEFT | KW_RIGHT | KW_FULL | KW_ON | KW_PARTITION | KW_PARTITIONS | KW_TABLE | KW_TABLES | KW_FUNCTIONS | KW_SHOW | KW_MSCK | KW_REPAIR | KW_DIRECTORY | KW_LOCAL | KW_TRANSFORM | KW_USING | KW_CLUSTER | KW_DISTRIBUTE | KW_SORT | KW_UNION | KW_LOAD | KW_DATA | KW_INPATH | KW_IS | KW_NULL | KW_CREATE | KW_EXTERNAL | KW_ALTER | KW_CHANGE | KW_COLUMN | KW_FIRST | KW_AFTER | KW_DESCRIBE | KW_DROP | KW_RENAME | KW_TO | KW_COMMENT | KW_BOOLEAN | KW_TINYINT | KW_SMALLINT | KW_INT | KW_BIGINT | KW_FLOAT | KW_DOUBLE | KW_DATE | KW_DATETIME | KW_TIMESTAMP | KW_STRING | KW_ARRAY | KW_STRUCT | KW_MAP | KW_UNIONTYPE | KW_REDUCE | KW_PARTITIONED | KW_CLUSTERED | KW_SORTED | KW_INTO | KW_BUCKETS | KW_ROW | KW_FORMAT | KW_DELIMITED | KW_FIELDS | KW_TERMINATED | KW_ESCAPED | KW_COLLECTION | KW_ITEMS | KW_KEYS | KW_KEY_TYPE | KW_LINES | KW_STORED | KW_FILEFORMAT | KW_SEQUENCEFILE | KW_TEXTFILE | KW_RCFILE | KW_INPUTFORMAT | KW_OUTPUTFORMAT | KW_LOCATION | KW_TABLESAMPLE | KW_BUCKET | KW_OUT | KW_OF | KW_CAST | KW_ADD | KW_REPLACE | KW_COLUMNS | KW_RLIKE | KW_REGEXP | KW_TEMPORARY | KW_FUNCTION | KW_EXPLAIN | KW_EXTENDED | KW_FORMATTED | KW_SERDE | KW_WITH | KW_SERDEPROPERTIES | KW_LIMIT | KW_SET | KW_TBLPROPERTIES | KW_VALUE_TYPE | KW_ELEM_TYPE | KW_CASE | KW_WHEN | KW_THEN | KW_ELSE | KW_END | KW_MAPJOIN | KW_STREAMTABLE | KW_CLUSTERSTATUS | KW_UTC | KW_UTCTIMESTAMP | KW_LONG | KW_DELETE | KW_PLUS | KW_MINUS | KW_FETCH | KW_INTERSECT | KW_VIEW | KW_IN | KW_DATABASE | KW_DATABASES | KW_MATERIALIZED | KW_SCHEMA | KW_SCHEMAS | KW_GRANT | KW_REVOKE | KW_SSL | KW_UNDO | KW_LOCK | KW_UNLOCK | KW_PROCEDURE | KW_UNSIGNED | KW_WHILE | KW_READ | KW_READS | KW_PURGE | KW_RANGE | KW_ANALYZE | KW_BEFORE | KW_BETWEEN | KW_BOTH | KW_BINARY | KW_CROSS | KW_CONTINUE | KW_CURSOR | KW_TRIGGER | KW_RECORDREADER | KW_RECORDWRITER | KW_SEMI | KW_LATERAL | KW_TOUCH | KW_ARCHIVE | KW_UNARCHIVE | KW_USE | KW_IDENTIFIED | KW_OPTION | KW_FOR | DOT | COLON | COMMA | SEMICOLON | LPAREN | RPAREN | LSQUARE | RSQUARE | LCURLY | RCURLY | EQUAL | NOTEQUAL | LESSTHANOREQUALTO | LESSTHAN | GREATERTHANOREQUALTO | GREATERTHAN | DIVIDE | PLUS | MINUS | STAR | MOD | DIV | AMPERSAND | TILDE | BITWISEOR | BITWISEXOR | QUESTION | DOLLAR | StringLiteral | CharSetLiteral | Number | Identifier | CharSetName | WS | COMMENT )
        int alt20=217;
        alt20 = dfa20.predict(input);
        switch (alt20) {
            case 1 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:10: KW_TRUE
                {
                mKW_TRUE(); 

                }
                break;
            case 2 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:18: KW_FALSE
                {
                mKW_FALSE(); 

                }
                break;
            case 3 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:27: KW_ALL
                {
                mKW_ALL(); 

                }
                break;
            case 4 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:34: KW_AND
                {
                mKW_AND(); 

                }
                break;
            case 5 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:41: KW_OR
                {
                mKW_OR(); 

                }
                break;
            case 6 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:47: KW_NOT
                {
                mKW_NOT(); 

                }
                break;
            case 7 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:54: KW_LIKE
                {
                mKW_LIKE(); 

                }
                break;
            case 8 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:62: KW_IF
                {
                mKW_IF(); 

                }
                break;
            case 9 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:68: KW_EXISTS
                {
                mKW_EXISTS(); 

                }
                break;
            case 10 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:78: KW_ASC
                {
                mKW_ASC(); 

                }
                break;
            case 11 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:85: KW_DESC
                {
                mKW_DESC(); 

                }
                break;
            case 12 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:93: KW_ORDER
                {
                mKW_ORDER(); 

                }
                break;
            case 13 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:102: KW_BY
                {
                mKW_BY(); 

                }
                break;
            case 14 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:108: KW_GROUP
                {
                mKW_GROUP(); 

                }
                break;
            case 15 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:117: KW_HAVING
                {
                mKW_HAVING(); 

                }
                break;
            case 16 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:127: KW_WHERE
                {
                mKW_WHERE(); 

                }
                break;
            case 17 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:136: KW_FROM
                {
                mKW_FROM(); 

                }
                break;
            case 18 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:144: KW_AS
                {
                mKW_AS(); 

                }
                break;
            case 19 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:150: KW_SELECT
                {
                mKW_SELECT(); 

                }
                break;
            case 20 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:160: KW_DISTINCT
                {
                mKW_DISTINCT(); 

                }
                break;
            case 21 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:172: KW_INSERT
                {
                mKW_INSERT(); 

                }
                break;
            case 22 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:182: KW_OVERWRITE
                {
                mKW_OVERWRITE(); 

                }
                break;
            case 23 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:195: KW_OUTER
                {
                mKW_OUTER(); 

                }
                break;
            case 24 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:204: KW_UNIQUEJOIN
                {
                mKW_UNIQUEJOIN(); 

                }
                break;
            case 25 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:218: KW_PRESERVE
                {
                mKW_PRESERVE(); 

                }
                break;
            case 26 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:230: KW_JOIN
                {
                mKW_JOIN(); 

                }
                break;
            case 27 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:238: KW_LEFT
                {
                mKW_LEFT(); 

                }
                break;
            case 28 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:246: KW_RIGHT
                {
                mKW_RIGHT(); 

                }
                break;
            case 29 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:255: KW_FULL
                {
                mKW_FULL(); 

                }
                break;
            case 30 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:263: KW_ON
                {
                mKW_ON(); 

                }
                break;
            case 31 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:269: KW_PARTITION
                {
                mKW_PARTITION(); 

                }
                break;
            case 32 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:282: KW_PARTITIONS
                {
                mKW_PARTITIONS(); 

                }
                break;
            case 33 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:296: KW_TABLE
                {
                mKW_TABLE(); 

                }
                break;
            case 34 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:305: KW_TABLES
                {
                mKW_TABLES(); 

                }
                break;
            case 35 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:315: KW_FUNCTIONS
                {
                mKW_FUNCTIONS(); 

                }
                break;
            case 36 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:328: KW_SHOW
                {
                mKW_SHOW(); 

                }
                break;
            case 37 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:336: KW_MSCK
                {
                mKW_MSCK(); 

                }
                break;
            case 38 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:344: KW_REPAIR
                {
                mKW_REPAIR(); 

                }
                break;
            case 39 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:354: KW_DIRECTORY
                {
                mKW_DIRECTORY(); 

                }
                break;
            case 40 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:367: KW_LOCAL
                {
                mKW_LOCAL(); 

                }
                break;
            case 41 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:376: KW_TRANSFORM
                {
                mKW_TRANSFORM(); 

                }
                break;
            case 42 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:389: KW_USING
                {
                mKW_USING(); 

                }
                break;
            case 43 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:398: KW_CLUSTER
                {
                mKW_CLUSTER(); 

                }
                break;
            case 44 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:409: KW_DISTRIBUTE
                {
                mKW_DISTRIBUTE(); 

                }
                break;
            case 45 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:423: KW_SORT
                {
                mKW_SORT(); 

                }
                break;
            case 46 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:431: KW_UNION
                {
                mKW_UNION(); 

                }
                break;
            case 47 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:440: KW_LOAD
                {
                mKW_LOAD(); 

                }
                break;
            case 48 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:448: KW_DATA
                {
                mKW_DATA(); 

                }
                break;
            case 49 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:456: KW_INPATH
                {
                mKW_INPATH(); 

                }
                break;
            case 50 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:466: KW_IS
                {
                mKW_IS(); 

                }
                break;
            case 51 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:472: KW_NULL
                {
                mKW_NULL(); 

                }
                break;
            case 52 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:480: KW_CREATE
                {
                mKW_CREATE(); 

                }
                break;
            case 53 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:490: KW_EXTERNAL
                {
                mKW_EXTERNAL(); 

                }
                break;
            case 54 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:502: KW_ALTER
                {
                mKW_ALTER(); 

                }
                break;
            case 55 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:511: KW_CHANGE
                {
                mKW_CHANGE(); 

                }
                break;
            case 56 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:521: KW_COLUMN
                {
                mKW_COLUMN(); 

                }
                break;
            case 57 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:531: KW_FIRST
                {
                mKW_FIRST(); 

                }
                break;
            case 58 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:540: KW_AFTER
                {
                mKW_AFTER(); 

                }
                break;
            case 59 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:549: KW_DESCRIBE
                {
                mKW_DESCRIBE(); 

                }
                break;
            case 60 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:561: KW_DROP
                {
                mKW_DROP(); 

                }
                break;
            case 61 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:569: KW_RENAME
                {
                mKW_RENAME(); 

                }
                break;
            case 62 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:579: KW_TO
                {
                mKW_TO(); 

                }
                break;
            case 63 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:585: KW_COMMENT
                {
                mKW_COMMENT(); 

                }
                break;
            case 64 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:596: KW_BOOLEAN
                {
                mKW_BOOLEAN(); 

                }
                break;
            case 65 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:607: KW_TINYINT
                {
                mKW_TINYINT(); 

                }
                break;
            case 66 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:618: KW_SMALLINT
                {
                mKW_SMALLINT(); 

                }
                break;
            case 67 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:630: KW_INT
                {
                mKW_INT(); 

                }
                break;
            case 68 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:637: KW_BIGINT
                {
                mKW_BIGINT(); 

                }
                break;
            case 69 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:647: KW_FLOAT
                {
                mKW_FLOAT(); 

                }
                break;
            case 70 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:656: KW_DOUBLE
                {
                mKW_DOUBLE(); 

                }
                break;
            case 71 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:666: KW_DATE
                {
                mKW_DATE(); 

                }
                break;
            case 72 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:674: KW_DATETIME
                {
                mKW_DATETIME(); 

                }
                break;
            case 73 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:686: KW_TIMESTAMP
                {
                mKW_TIMESTAMP(); 

                }
                break;
            case 74 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:699: KW_STRING
                {
                mKW_STRING(); 

                }
                break;
            case 75 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:709: KW_ARRAY
                {
                mKW_ARRAY(); 

                }
                break;
            case 76 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:718: KW_STRUCT
                {
                mKW_STRUCT(); 

                }
                break;
            case 77 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:728: KW_MAP
                {
                mKW_MAP(); 

                }
                break;
            case 78 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:735: KW_UNIONTYPE
                {
                mKW_UNIONTYPE(); 

                }
                break;
            case 79 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:748: KW_REDUCE
                {
                mKW_REDUCE(); 

                }
                break;
            case 80 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:758: KW_PARTITIONED
                {
                mKW_PARTITIONED(); 

                }
                break;
            case 81 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:773: KW_CLUSTERED
                {
                mKW_CLUSTERED(); 

                }
                break;
            case 82 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:786: KW_SORTED
                {
                mKW_SORTED(); 

                }
                break;
            case 83 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:796: KW_INTO
                {
                mKW_INTO(); 

                }
                break;
            case 84 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:804: KW_BUCKETS
                {
                mKW_BUCKETS(); 

                }
                break;
            case 85 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:815: KW_ROW
                {
                mKW_ROW(); 

                }
                break;
            case 86 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:822: KW_FORMAT
                {
                mKW_FORMAT(); 

                }
                break;
            case 87 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:832: KW_DELIMITED
                {
                mKW_DELIMITED(); 

                }
                break;
            case 88 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:845: KW_FIELDS
                {
                mKW_FIELDS(); 

                }
                break;
            case 89 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:855: KW_TERMINATED
                {
                mKW_TERMINATED(); 

                }
                break;
            case 90 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:869: KW_ESCAPED
                {
                mKW_ESCAPED(); 

                }
                break;
            case 91 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:880: KW_COLLECTION
                {
                mKW_COLLECTION(); 

                }
                break;
            case 92 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:894: KW_ITEMS
                {
                mKW_ITEMS(); 

                }
                break;
            case 93 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:903: KW_KEYS
                {
                mKW_KEYS(); 

                }
                break;
            case 94 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:911: KW_KEY_TYPE
                {
                mKW_KEY_TYPE(); 

                }
                break;
            case 95 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:923: KW_LINES
                {
                mKW_LINES(); 

                }
                break;
            case 96 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:932: KW_STORED
                {
                mKW_STORED(); 

                }
                break;
            case 97 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:942: KW_FILEFORMAT
                {
                mKW_FILEFORMAT(); 

                }
                break;
            case 98 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:956: KW_SEQUENCEFILE
                {
                mKW_SEQUENCEFILE(); 

                }
                break;
            case 99 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:972: KW_TEXTFILE
                {
                mKW_TEXTFILE(); 

                }
                break;
            case 100 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:984: KW_RCFILE
                {
                mKW_RCFILE(); 

                }
                break;
            case 101 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:994: KW_INPUTFORMAT
                {
                mKW_INPUTFORMAT(); 

                }
                break;
            case 102 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1009: KW_OUTPUTFORMAT
                {
                mKW_OUTPUTFORMAT(); 

                }
                break;
            case 103 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1025: KW_LOCATION
                {
                mKW_LOCATION(); 

                }
                break;
            case 104 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1037: KW_TABLESAMPLE
                {
                mKW_TABLESAMPLE(); 

                }
                break;
            case 105 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1052: KW_BUCKET
                {
                mKW_BUCKET(); 

                }
                break;
            case 106 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1062: KW_OUT
                {
                mKW_OUT(); 

                }
                break;
            case 107 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1069: KW_OF
                {
                mKW_OF(); 

                }
                break;
            case 108 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1075: KW_CAST
                {
                mKW_CAST(); 

                }
                break;
            case 109 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1083: KW_ADD
                {
                mKW_ADD(); 

                }
                break;
            case 110 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1090: KW_REPLACE
                {
                mKW_REPLACE(); 

                }
                break;
            case 111 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1101: KW_COLUMNS
                {
                mKW_COLUMNS(); 

                }
                break;
            case 112 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1112: KW_RLIKE
                {
                mKW_RLIKE(); 

                }
                break;
            case 113 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1121: KW_REGEXP
                {
                mKW_REGEXP(); 

                }
                break;
            case 114 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1131: KW_TEMPORARY
                {
                mKW_TEMPORARY(); 

                }
                break;
            case 115 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1144: KW_FUNCTION
                {
                mKW_FUNCTION(); 

                }
                break;
            case 116 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1156: KW_EXPLAIN
                {
                mKW_EXPLAIN(); 

                }
                break;
            case 117 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1167: KW_EXTENDED
                {
                mKW_EXTENDED(); 

                }
                break;
            case 118 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1179: KW_FORMATTED
                {
                mKW_FORMATTED(); 

                }
                break;
            case 119 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1192: KW_SERDE
                {
                mKW_SERDE(); 

                }
                break;
            case 120 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1201: KW_WITH
                {
                mKW_WITH(); 

                }
                break;
            case 121 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1209: KW_SERDEPROPERTIES
                {
                mKW_SERDEPROPERTIES(); 

                }
                break;
            case 122 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1228: KW_LIMIT
                {
                mKW_LIMIT(); 

                }
                break;
            case 123 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1237: KW_SET
                {
                mKW_SET(); 

                }
                break;
            case 124 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1244: KW_TBLPROPERTIES
                {
                mKW_TBLPROPERTIES(); 

                }
                break;
            case 125 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1261: KW_VALUE_TYPE
                {
                mKW_VALUE_TYPE(); 

                }
                break;
            case 126 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1275: KW_ELEM_TYPE
                {
                mKW_ELEM_TYPE(); 

                }
                break;
            case 127 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1288: KW_CASE
                {
                mKW_CASE(); 

                }
                break;
            case 128 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1296: KW_WHEN
                {
                mKW_WHEN(); 

                }
                break;
            case 129 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1304: KW_THEN
                {
                mKW_THEN(); 

                }
                break;
            case 130 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1312: KW_ELSE
                {
                mKW_ELSE(); 

                }
                break;
            case 131 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1320: KW_END
                {
                mKW_END(); 

                }
                break;
            case 132 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1327: KW_MAPJOIN
                {
                mKW_MAPJOIN(); 

                }
                break;
            case 133 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1338: KW_STREAMTABLE
                {
                mKW_STREAMTABLE(); 

                }
                break;
            case 134 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1353: KW_CLUSTERSTATUS
                {
                mKW_CLUSTERSTATUS(); 

                }
                break;
            case 135 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1370: KW_UTC
                {
                mKW_UTC(); 

                }
                break;
            case 136 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1377: KW_UTCTIMESTAMP
                {
                mKW_UTCTIMESTAMP(); 

                }
                break;
            case 137 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1393: KW_LONG
                {
                mKW_LONG(); 

                }
                break;
            case 138 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1401: KW_DELETE
                {
                mKW_DELETE(); 

                }
                break;
            case 139 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1411: KW_PLUS
                {
                mKW_PLUS(); 

                }
                break;
            case 140 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1419: KW_MINUS
                {
                mKW_MINUS(); 

                }
                break;
            case 141 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1428: KW_FETCH
                {
                mKW_FETCH(); 

                }
                break;
            case 142 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1437: KW_INTERSECT
                {
                mKW_INTERSECT(); 

                }
                break;
            case 143 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1450: KW_VIEW
                {
                mKW_VIEW(); 

                }
                break;
            case 144 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1458: KW_IN
                {
                mKW_IN(); 

                }
                break;
            case 145 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1464: KW_DATABASE
                {
                mKW_DATABASE(); 

                }
                break;
            case 146 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1476: KW_DATABASES
                {
                mKW_DATABASES(); 

                }
                break;
            case 147 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1489: KW_MATERIALIZED
                {
                mKW_MATERIALIZED(); 

                }
                break;
            case 148 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1505: KW_SCHEMA
                {
                mKW_SCHEMA(); 

                }
                break;
            case 149 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1515: KW_SCHEMAS
                {
                mKW_SCHEMAS(); 

                }
                break;
            case 150 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1526: KW_GRANT
                {
                mKW_GRANT(); 

                }
                break;
            case 151 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1535: KW_REVOKE
                {
                mKW_REVOKE(); 

                }
                break;
            case 152 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1545: KW_SSL
                {
                mKW_SSL(); 

                }
                break;
            case 153 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1552: KW_UNDO
                {
                mKW_UNDO(); 

                }
                break;
            case 154 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1560: KW_LOCK
                {
                mKW_LOCK(); 

                }
                break;
            case 155 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1568: KW_UNLOCK
                {
                mKW_UNLOCK(); 

                }
                break;
            case 156 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1578: KW_PROCEDURE
                {
                mKW_PROCEDURE(); 

                }
                break;
            case 157 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1591: KW_UNSIGNED
                {
                mKW_UNSIGNED(); 

                }
                break;
            case 158 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1603: KW_WHILE
                {
                mKW_WHILE(); 

                }
                break;
            case 159 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1612: KW_READ
                {
                mKW_READ(); 

                }
                break;
            case 160 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1620: KW_READS
                {
                mKW_READS(); 

                }
                break;
            case 161 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1629: KW_PURGE
                {
                mKW_PURGE(); 

                }
                break;
            case 162 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1638: KW_RANGE
                {
                mKW_RANGE(); 

                }
                break;
            case 163 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1647: KW_ANALYZE
                {
                mKW_ANALYZE(); 

                }
                break;
            case 164 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1658: KW_BEFORE
                {
                mKW_BEFORE(); 

                }
                break;
            case 165 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1668: KW_BETWEEN
                {
                mKW_BETWEEN(); 

                }
                break;
            case 166 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1679: KW_BOTH
                {
                mKW_BOTH(); 

                }
                break;
            case 167 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1687: KW_BINARY
                {
                mKW_BINARY(); 

                }
                break;
            case 168 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1697: KW_CROSS
                {
                mKW_CROSS(); 

                }
                break;
            case 169 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1706: KW_CONTINUE
                {
                mKW_CONTINUE(); 

                }
                break;
            case 170 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1718: KW_CURSOR
                {
                mKW_CURSOR(); 

                }
                break;
            case 171 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1728: KW_TRIGGER
                {
                mKW_TRIGGER(); 

                }
                break;
            case 172 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1739: KW_RECORDREADER
                {
                mKW_RECORDREADER(); 

                }
                break;
            case 173 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1755: KW_RECORDWRITER
                {
                mKW_RECORDWRITER(); 

                }
                break;
            case 174 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1771: KW_SEMI
                {
                mKW_SEMI(); 

                }
                break;
            case 175 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1779: KW_LATERAL
                {
                mKW_LATERAL(); 

                }
                break;
            case 176 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1790: KW_TOUCH
                {
                mKW_TOUCH(); 

                }
                break;
            case 177 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1799: KW_ARCHIVE
                {
                mKW_ARCHIVE(); 

                }
                break;
            case 178 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1810: KW_UNARCHIVE
                {
                mKW_UNARCHIVE(); 

                }
                break;
            case 179 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1823: KW_USE
                {
                mKW_USE(); 

                }
                break;
            case 180 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1830: KW_IDENTIFIED
                {
                mKW_IDENTIFIED(); 

                }
                break;
            case 181 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1844: KW_OPTION
                {
                mKW_OPTION(); 

                }
                break;
            case 182 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1854: KW_FOR
                {
                mKW_FOR(); 

                }
                break;
            case 183 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1861: DOT
                {
                mDOT(); 

                }
                break;
            case 184 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1865: COLON
                {
                mCOLON(); 

                }
                break;
            case 185 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1871: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 186 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1877: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 187 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1887: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 188 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1894: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 189 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1901: LSQUARE
                {
                mLSQUARE(); 

                }
                break;
            case 190 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1909: RSQUARE
                {
                mRSQUARE(); 

                }
                break;
            case 191 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1917: LCURLY
                {
                mLCURLY(); 

                }
                break;
            case 192 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1924: RCURLY
                {
                mRCURLY(); 

                }
                break;
            case 193 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1931: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 194 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1937: NOTEQUAL
                {
                mNOTEQUAL(); 

                }
                break;
            case 195 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1946: LESSTHANOREQUALTO
                {
                mLESSTHANOREQUALTO(); 

                }
                break;
            case 196 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1964: LESSTHAN
                {
                mLESSTHAN(); 

                }
                break;
            case 197 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1973: GREATERTHANOREQUALTO
                {
                mGREATERTHANOREQUALTO(); 

                }
                break;
            case 198 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:1994: GREATERTHAN
                {
                mGREATERTHAN(); 

                }
                break;
            case 199 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2006: DIVIDE
                {
                mDIVIDE(); 

                }
                break;
            case 200 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2013: PLUS
                {
                mPLUS(); 

                }
                break;
            case 201 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2018: MINUS
                {
                mMINUS(); 

                }
                break;
            case 202 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2024: STAR
                {
                mSTAR(); 

                }
                break;
            case 203 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2029: MOD
                {
                mMOD(); 

                }
                break;
            case 204 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2033: DIV
                {
                mDIV(); 

                }
                break;
            case 205 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2037: AMPERSAND
                {
                mAMPERSAND(); 

                }
                break;
            case 206 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2047: TILDE
                {
                mTILDE(); 

                }
                break;
            case 207 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2053: BITWISEOR
                {
                mBITWISEOR(); 

                }
                break;
            case 208 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2063: BITWISEXOR
                {
                mBITWISEXOR(); 

                }
                break;
            case 209 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2074: QUESTION
                {
                mQUESTION(); 

                }
                break;
            case 210 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2083: DOLLAR
                {
                mDOLLAR(); 

                }
                break;
            case 211 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2090: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 212 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2104: CharSetLiteral
                {
                mCharSetLiteral(); 

                }
                break;
            case 213 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2119: Number
                {
                mNumber(); 

                }
                break;
            case 214 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2126: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 215 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2137: CharSetName
                {
                mCharSetName(); 

                }
                break;
            case 216 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2149: WS
                {
                mWS(); 

                }
                break;
            case 217 :
                // /home/yuanhang.ghj/dev/hive/hive_1.4/dev-1.4/dev_hive-1.1.4/ql/src/java/org/apache/hadoop/hive/ql/parse/Hive.g:1:2152: COMMENT
                {
                mCOMMENT(); 

                }
                break;

        }

    }


    protected DFA20 dfa20 = new DFA20(this);
    static final String DFA20_eotS =
        "\1\uffff\5\65\1\124\20\65\1\u0092\1\65\13\uffff\1\u0095\1\u0097"+
        "\2\uffff\1\u0099\11\uffff\1\63\1\uffff\1\65\2\uffff\1\65\1\u00a7"+
        "\16\65\1\u00bf\3\65\1\u00c5\3\65\1\u00c9\1\u00ca\2\65\2\uffff\4"+
        "\65\1\u00d8\1\u00d9\1\65\1\u00db\15\65\1\u00f1\47\65\1\uffff\1\65"+
        "\10\uffff\1\u0132\2\uffff\1\u0132\1\uffff\1\65\1\uffff\4\65\1\uffff"+
        "\15\65\1\u0146\5\65\1\u014c\2\65\1\u014f\1\uffff\1\65\1\u0151\1"+
        "\65\1\u0153\1\65\1\uffff\1\65\1\u0158\1\65\2\uffff\1\124\11\65\1"+
        "\u0166\2\65\2\uffff\1\65\1\uffff\6\65\1\u0171\4\65\1\u0178\11\65"+
        "\1\uffff\13\65\1\u018e\1\65\1\u0190\12\65\1\u019f\1\65\1\u01a1\17"+
        "\65\1\u01b2\3\65\1\u01b7\20\65\3\uffff\2\65\1\u01cc\7\65\1\u01d4"+
        "\2\65\1\u01d7\1\65\1\u01d9\2\65\1\uffff\5\65\1\uffff\2\65\1\uffff"+
        "\1\65\1\uffff\1\65\1\uffff\4\65\1\uffff\1\65\1\u01ea\2\65\1\u01ee"+
        "\1\u01ef\1\u01f0\2\65\1\u01f3\1\u01f4\1\65\1\u01f6\1\uffff\11\65"+
        "\1\u0201\1\uffff\1\u0203\2\65\1\u0207\1\u0209\1\65\1\uffff\1\65"+
        "\1\u020d\1\65\1\u020f\12\65\1\u021a\1\65\1\u021c\3\65\1\u0220\1"+
        "\uffff\1\65\1\uffff\1\u0222\1\65\1\u0225\6\65\1\u022c\4\65\1\uffff"+
        "\1\65\1\uffff\3\65\1\u0235\1\65\1\u0237\2\65\1\u023b\7\65\1\uffff"+
        "\4\65\1\uffff\1\65\1\u0248\11\65\1\u0252\1\u0253\1\u0254\3\65\1"+
        "\u0258\2\65\1\uffff\1\u025b\1\u025d\5\65\1\uffff\1\65\1\u0264\1"+
        "\uffff\1\u0265\1\uffff\2\65\1\u0268\2\65\1\u026b\2\65\1\u026e\1"+
        "\u026f\1\u0270\1\u0271\1\65\1\u0273\2\65\1\uffff\2\65\1\u0278\3"+
        "\uffff\1\u0279\1\u027a\2\uffff\1\65\1\uffff\4\65\1\u0280\5\65\1"+
        "\uffff\1\65\1\uffff\3\65\1\uffff\1\65\1\uffff\3\65\1\uffff\1\65"+
        "\1\uffff\6\65\1\u0295\1\u0296\1\65\1\u0298\1\uffff\1\u0299\1\uffff"+
        "\1\65\1\u029c\1\65\1\uffff\1\65\1\uffff\2\65\1\uffff\6\65\1\uffff"+
        "\1\65\1\u02a9\2\65\1\u02ac\1\u02ad\2\65\1\uffff\1\65\1\uffff\2\65"+
        "\1\u02b3\1\uffff\5\65\1\u02b9\1\u02ba\1\65\1\u02bc\1\u02bd\2\65"+
        "\1\uffff\6\65\1\u02c6\2\65\3\uffff\1\u02c9\2\65\1\uffff\2\65\1\uffff"+
        "\1\u02cf\1\uffff\6\65\2\uffff\1\65\1\u02d8\1\uffff\1\u02d9\1\65"+
        "\1\uffff\2\65\4\uffff\1\u02dd\1\uffff\4\65\3\uffff\1\65\1\u02e3"+
        "\1\65\1\u02e5\1\65\1\uffff\2\65\1\u02e9\3\65\1\u02ed\6\65\1\u02f4"+
        "\1\65\1\u02f6\1\u02f7\1\u02f8\1\65\1\u02fb\2\uffff\1\u02fc\2\uffff"+
        "\1\u02fd\1\65\1\uffff\1\65\1\u0301\1\65\1\u0303\1\u0304\1\65\1\u0306"+
        "\1\u0307\4\65\1\uffff\1\u030c\1\65\2\uffff\3\65\1\u0311\1\65\1\uffff"+
        "\1\u0313\1\65\1\u0316\1\u0317\1\u0318\2\uffff\1\u0319\2\uffff\2"+
        "\65\1\u031c\2\65\1\u0320\1\65\1\u0322\1\uffff\1\65\1\u0324\1\uffff"+
        "\1\65\1\u0326\1\65\1\u0328\1\65\1\uffff\1\65\1\u032b\6\65\2\uffff"+
        "\1\65\1\u0333\1\u0334\1\uffff\2\65\1\u0337\2\65\1\uffff\1\65\1\uffff"+
        "\3\65\1\uffff\1\u033e\1\u033f\1\65\1\uffff\6\65\1\uffff\1\u0347"+
        "\3\uffff\1\u0348\1\u0349\3\uffff\2\65\1\u034c\1\uffff\1\65\2\uffff"+
        "\1\65\2\uffff\4\65\1\uffff\4\65\1\uffff\1\u0357\1\uffff\2\65\4\uffff"+
        "\1\u035a\1\65\1\uffff\1\u035c\1\65\1\u035e\1\uffff\1\65\1\uffff"+
        "\1\u0362\1\uffff\1\u0363\1\uffff\1\65\1\uffff\2\65\1\uffff\1\u0367"+
        "\3\65\1\u036c\2\65\2\uffff\2\65\1\uffff\1\u0371\3\65\1\u0375\1\u0376"+
        "\2\uffff\1\u0377\1\65\1\u037a\1\u037b\1\u037c\2\65\3\uffff\2\65"+
        "\1\uffff\1\u0381\2\65\1\u0384\4\65\1\u0389\1\65\1\uffff\2\65\1\uffff"+
        "\1\65\1\uffff\1\65\1\uffff\1\u038f\2\65\2\uffff\1\u0392\1\65\1\u0394"+
        "\1\uffff\1\u0395\2\65\1\u0398\1\uffff\1\u0399\2\65\1\u039c\1\uffff"+
        "\1\u039d\2\65\3\uffff\1\u03a0\1\u03a1\3\uffff\1\65\1\u03a3\2\65"+
        "\1\uffff\1\65\1\u03a7\1\uffff\1\65\1\u03a9\1\65\1\u03ab\1\uffff"+
        "\1\u03ae\4\65\1\uffff\1\65\1\u03b4\1\uffff\1\65\2\uffff\1\u03b6"+
        "\1\65\2\uffff\1\u03b8\1\65\2\uffff\1\65\1\u03bb\2\uffff\1\u03bc"+
        "\1\uffff\3\65\1\uffff\1\u03c0\1\uffff\1\65\1\uffff\1\65\1\u03c3"+
        "\1\uffff\3\65\1\u03c7\1\65\1\uffff\1\u03c9\1\uffff\1\65\1\uffff"+
        "\1\65\1\u03cc\2\uffff\2\65\1\u03cf\1\uffff\1\65\1\u03d1\1\uffff"+
        "\3\65\1\uffff\1\65\1\uffff\1\65\1\u03d7\1\uffff\1\65\1\u03d9\1\uffff"+
        "\1\u03da\1\uffff\1\u03db\1\u03dc\1\u03dd\1\65\1\u03df\1\uffff\1"+
        "\65\5\uffff\1\u03e1\1\uffff\1\65\1\uffff\1\u03e3\1\uffff";
    static final String DFA20_eofS =
        "\u03e4\uffff";
    static final String DFA20_minS =
        "\1\11\2\101\1\104\1\106\1\117\1\75\1\101\1\104\1\114\1\101\1\105"+
        "\1\122\1\101\1\110\1\103\1\116\1\101\1\117\3\101\1\105\1\44\1\111"+
        "\13\uffff\2\75\2\uffff\1\55\7\uffff\2\0\1\130\1\uffff\1\55\2\uffff"+
        "\1\101\1\44\1\102\2\115\1\105\1\114\1\124\1\117\2\114\1\122\1\105"+
        "\1\117\1\101\1\103\1\44\1\114\1\124\1\104\1\44\2\124\1\105\2\44"+
        "\1\124\1\114\2\uffff\1\124\1\101\1\113\1\106\2\44\1\105\1\44\1\105"+
        "\1\111\1\103\1\123\1\104\1\114\1\124\1\122\1\117\1\125\1\117\1\107"+
        "\1\106\1\44\1\103\1\101\1\126\1\105\1\124\1\114\1\110\1\114\1\117"+
        "\1\101\1\122\1\117\1\101\1\103\1\105\1\122\1\105\1\125\1\122\1\111"+
        "\1\101\1\116\1\107\1\127\1\106\1\111\1\116\1\120\1\103\1\122\1\114"+
        "\1\105\1\125\1\101\1\123\1\131\1\105\1\101\1\114\1\uffff\1\105\6"+
        "\uffff\2\0\1\42\2\0\1\42\1\uffff\1\55\1\uffff\1\116\1\107\1\105"+
        "\1\103\1\uffff\1\114\1\105\1\131\1\124\1\120\1\115\1\116\1\120\1"+
        "\103\1\115\1\123\1\114\1\103\1\44\1\123\1\114\1\105\1\101\1\114"+
        "\1\44\1\110\1\101\1\44\1\uffff\1\105\1\44\1\105\1\44\1\105\1\uffff"+
        "\1\111\1\44\1\122\2\uffff\1\44\1\114\1\105\1\101\1\104\1\107\1\111"+
        "\2\105\1\124\1\44\1\105\1\101\2\uffff\1\116\1\uffff\1\115\1\105"+
        "\1\123\1\114\1\101\1\105\1\44\1\103\1\105\1\101\1\124\1\44\1\105"+
        "\1\120\1\102\1\110\1\114\1\111\1\101\1\117\1\127\1\uffff\1\113\1"+
        "\116\1\125\1\111\1\116\1\114\1\110\1\105\1\104\1\125\1\111\1\44"+
        "\1\105\1\44\1\127\1\114\1\124\1\105\2\122\1\111\3\117\1\44\1\116"+
        "\1\44\1\107\1\103\2\123\1\124\1\116\1\101\1\104\1\125\1\117\1\101"+
        "\1\105\1\117\1\107\1\110\1\44\1\111\1\113\1\125\1\44\1\105\1\113"+
        "\1\123\1\115\1\114\1\124\1\101\2\123\1\116\1\105\1\123\1\131\1\114"+
        "\1\105\1\127\1\0\1\uffff\1\0\1\123\1\107\1\44\1\110\1\105\1\123"+
        "\1\111\1\106\1\117\1\111\1\44\1\122\1\110\1\44\1\105\1\44\1\124"+
        "\1\101\1\uffff\1\124\1\104\1\106\1\124\1\131\1\uffff\1\111\1\131"+
        "\1\uffff\1\122\1\uffff\1\122\1\uffff\1\122\1\117\1\122\1\125\1\uffff"+
        "\1\127\1\44\1\122\1\114\3\44\1\124\1\123\2\44\1\122\1\44\1\uffff"+
        "\1\122\3\124\1\123\1\116\1\124\1\101\1\120\1\44\1\uffff\1\44\1\124"+
        "\1\115\2\44\1\111\1\uffff\1\103\1\44\1\114\1\44\1\105\1\116\2\122"+
        "\2\105\1\124\1\120\1\116\1\105\1\44\1\105\1\44\1\103\2\105\1\44"+
        "\1\uffff\1\115\1\uffff\1\44\1\114\1\44\1\116\1\101\1\103\1\105\1"+
        "\103\1\107\1\44\1\125\1\116\1\103\1\124\1\uffff\1\107\1\uffff\3"+
        "\105\1\44\1\111\1\44\1\111\1\101\1\44\1\103\1\122\1\115\1\130\1"+
        "\113\1\105\1\124\1\uffff\1\114\1\105\1\123\1\117\1\uffff\1\122\1"+
        "\44\1\117\2\105\1\115\1\111\1\124\1\123\1\124\1\107\4\44\1\125\1"+
        "\115\1\44\1\106\1\105\1\uffff\2\44\1\124\1\116\1\111\1\122\1\116"+
        "\1\uffff\1\117\1\44\1\uffff\1\44\1\uffff\1\111\1\124\1\44\1\123"+
        "\1\117\1\44\1\132\1\126\4\44\1\116\1\44\1\124\1\122\1\uffff\1\101"+
        "\1\111\1\44\3\uffff\2\44\2\uffff\1\123\1\uffff\1\124\1\106\1\110"+
        "\1\111\1\44\1\116\1\104\1\123\1\111\1\105\1\uffff\1\111\1\uffff"+
        "\1\105\1\111\1\101\1\uffff\1\111\1\uffff\1\116\1\111\1\124\1\uffff"+
        "\1\105\1\uffff\1\101\1\124\1\131\2\105\1\124\2\44\1\107\1\44\1\uffff"+
        "\1\44\1\uffff\1\124\1\44\1\116\1\uffff\1\101\1\uffff\1\111\1\104"+
        "\1\uffff\1\107\1\115\1\124\1\104\1\110\1\116\1\uffff\1\105\1\44"+
        "\1\113\1\115\2\44\1\104\1\122\1\uffff\1\124\1\uffff\1\122\1\103"+
        "\1\44\1\uffff\1\105\1\104\1\105\1\120\1\105\2\44\1\105\2\44\2\111"+
        "\1\uffff\1\122\1\116\1\103\2\116\1\105\1\44\2\105\3\uffff\1\44\1"+
        "\105\1\44\1\uffff\1\117\1\122\1\uffff\1\44\1\uffff\1\101\1\124\1"+
        "\114\2\101\1\120\2\uffff\1\117\1\44\1\uffff\1\44\1\122\1\uffff\2"+
        "\105\4\uffff\1\44\1\uffff\1\106\1\111\1\114\1\117\3\uffff\1\105"+
        "\1\44\1\117\1\44\1\106\1\uffff\1\101\1\105\1\44\1\116\1\104\1\102"+
        "\1\44\1\124\1\123\1\115\1\103\1\102\1\117\1\44\1\116\3\44\1\116"+
        "\1\44\2\uffff\1\44\2\uffff\1\44\1\122\1\uffff\1\103\1\44\1\116\2"+
        "\44\1\124\2\44\1\111\1\105\1\112\1\131\1\uffff\1\44\1\105\2\uffff"+
        "\1\125\1\126\1\111\1\44\1\105\1\uffff\1\44\1\122\3\44\2\uffff\1"+
        "\44\2\uffff\1\116\1\101\1\44\2\124\1\44\1\125\1\44\1\uffff\1\122"+
        "\1\44\1\uffff\2\44\1\122\1\44\1\115\1\uffff\1\115\1\44\1\105\1\122"+
        "\1\124\1\105\1\116\1\105\2\uffff\1\115\2\44\1\uffff\1\117\1\124"+
        "\1\44\1\116\1\103\1\uffff\1\122\1\uffff\1\111\1\114\1\104\1\uffff"+
        "\2\44\1\105\1\uffff\3\105\1\124\1\125\1\122\1\uffff\1\44\3\uffff"+
        "\2\44\3\uffff\1\117\1\105\1\44\1\uffff\1\124\2\uffff\1\101\2\uffff"+
        "\1\126\1\104\1\117\1\120\1\uffff\1\123\1\122\1\105\1\117\1\uffff"+
        "\1\44\1\uffff\1\122\1\105\4\uffff\1\44\1\114\1\uffff\1\44\1\111"+
        "\1\44\1\uffff\1\105\1\uffff\1\44\1\uffff\1\44\1\uffff\1\115\1\uffff"+
        "\2\120\1\uffff\1\44\1\131\1\105\1\122\1\44\1\104\1\101\2\uffff\1"+
        "\122\1\105\1\uffff\1\44\1\124\1\115\1\105\2\44\2\uffff\1\44\1\104"+
        "\3\44\1\124\1\131\3\uffff\1\120\1\106\1\uffff\1\44\1\102\1\105\1"+
        "\44\1\111\1\105\1\124\1\105\1\44\1\116\1\uffff\1\111\1\101\1\uffff"+
        "\1\111\1\uffff\1\117\1\uffff\1\44\1\124\1\104\2\uffff\1\44\1\114"+
        "\1\44\1\uffff\1\44\1\104\1\124\1\44\1\uffff\1\44\1\124\1\115\1\44"+
        "\1\uffff\1\44\1\101\1\104\3\uffff\2\44\3\uffff\1\105\1\44\1\105"+
        "\1\111\1\uffff\1\114\1\44\1\uffff\1\116\1\44\1\101\1\44\1\uffff"+
        "\1\44\1\124\1\104\1\132\1\116\1\uffff\1\101\1\44\1\uffff\1\105\2"+
        "\uffff\1\44\1\111\2\uffff\1\44\1\101\2\uffff\1\124\1\44\2\uffff"+
        "\1\44\1\uffff\1\122\1\114\1\105\1\uffff\1\44\1\uffff\1\115\1\uffff"+
        "\1\104\1\44\1\uffff\3\105\1\44\1\124\1\uffff\1\44\1\uffff\1\105"+
        "\1\uffff\1\124\1\44\2\uffff\1\124\1\105\1\44\1\uffff\1\120\1\44"+
        "\1\uffff\2\122\1\104\1\uffff\1\125\1\uffff\1\123\1\44\1\uffff\1"+
        "\111\1\44\1\uffff\1\44\1\uffff\3\44\1\123\1\44\1\uffff\1\105\5\uffff"+
        "\1\44\1\uffff\1\123\1\uffff\1\44\1\uffff";
    static final String DFA20_maxS =
        "\1\ufaff\1\122\1\125\1\123\1\126\1\125\1\75\1\117\1\124\1\130\1"+
        "\122\1\131\1\122\1\101\1\111\2\124\1\125\2\117\1\123\1\125\1\105"+
        "\1\ufaff\1\111\13\uffff\1\76\1\75\2\uffff\1\55\7\uffff\2\ufffe\1"+
        "\130\1\uffff\1\172\2\uffff\1\125\1\ufaff\1\102\1\116\1\130\1\105"+
        "\1\114\1\124\1\117\1\114\1\116\2\122\1\117\1\104\1\122\1\ufaff\2"+
        "\124\1\104\1\ufaff\2\124\1\105\2\ufaff\1\124\1\114\2\uffff\1\124"+
        "\2\116\1\106\2\ufaff\1\105\1\ufaff\1\105\1\124\1\103\1\123\1\104"+
        "\1\123\1\124\1\126\1\117\1\125\1\124\1\116\1\124\1\ufaff\1\103\1"+
        "\117\1\126\1\111\2\124\1\110\1\114\1\117\1\101\2\122\1\123\1\103"+
        "\1\111\1\122\1\117\1\125\1\122\1\111\1\126\1\116\1\107\1\127\1\106"+
        "\1\111\1\116\1\124\1\103\1\122\1\116\1\117\1\125\1\101\1\123\1\131"+
        "\1\105\1\101\1\114\1\uffff\1\105\6\uffff\2\ufffe\1\47\2\ufffe\1"+
        "\47\1\uffff\1\172\1\uffff\1\116\1\107\1\105\1\103\1\uffff\1\114"+
        "\1\105\1\131\1\124\1\120\1\115\1\116\1\120\1\103\1\115\1\123\1\114"+
        "\1\103\1\ufaff\1\123\1\114\1\105\1\101\1\114\1\ufaff\1\110\1\101"+
        "\1\ufaff\1\uffff\1\105\1\ufaff\1\105\1\ufaff\1\105\1\uffff\1\111"+
        "\1\ufaff\1\122\2\uffff\1\ufaff\1\114\1\105\1\113\1\104\1\107\1\111"+
        "\2\105\1\124\1\ufaff\1\105\1\125\2\uffff\1\116\1\uffff\1\115\1\105"+
        "\1\123\1\114\1\101\1\105\1\ufaff\1\103\1\111\1\105\1\124\1\ufaff"+
        "\1\105\1\120\1\102\1\110\1\114\1\111\1\101\1\117\1\127\1\uffff\1"+
        "\113\1\116\1\125\1\111\1\122\1\114\1\110\1\105\1\104\1\125\1\111"+
        "\1\ufaff\1\105\1\ufaff\1\127\1\114\1\124\1\125\2\122\1\111\1\117"+
        "\1\121\1\117\1\ufaff\1\116\1\ufaff\1\107\1\103\2\123\1\124\1\116"+
        "\1\114\1\104\1\125\1\117\1\101\1\105\1\117\1\107\1\110\1\ufaff\1"+
        "\111\1\113\1\125\1\ufaff\1\105\1\113\1\123\1\115\1\125\1\124\1\101"+
        "\2\123\1\116\1\124\1\123\1\131\1\114\1\105\1\127\1\ufffe\1\uffff"+
        "\1\ufffe\1\123\1\107\1\ufaff\1\110\1\105\1\123\1\111\1\106\1\117"+
        "\1\111\1\ufaff\1\122\1\110\1\ufaff\1\105\1\ufaff\1\124\1\101\1\uffff"+
        "\1\124\1\104\1\106\1\124\1\131\1\uffff\1\111\1\131\1\uffff\1\122"+
        "\1\uffff\1\122\1\uffff\1\122\1\117\1\122\1\125\1\uffff\1\127\1\ufaff"+
        "\1\122\1\124\3\ufaff\1\124\1\123\2\ufaff\1\122\1\ufaff\1\uffff\1"+
        "\122\3\124\1\123\1\122\1\124\1\101\1\120\1\ufaff\1\uffff\1\ufaff"+
        "\1\124\1\115\2\ufaff\1\122\1\uffff\1\103\1\ufaff\1\114\1\ufaff\1"+
        "\105\1\116\2\122\2\105\1\124\1\120\1\116\1\105\1\ufaff\1\105\1\ufaff"+
        "\1\103\2\105\1\ufaff\1\uffff\1\115\1\uffff\1\ufaff\1\114\1\ufaff"+
        "\1\116\1\101\1\103\1\105\1\103\1\107\1\ufaff\1\125\1\116\1\103\1"+
        "\124\1\uffff\1\107\1\uffff\3\105\1\ufaff\1\111\1\ufaff\1\111\1\101"+
        "\1\ufaff\1\103\1\122\1\115\1\130\1\113\1\105\1\124\1\uffff\1\114"+
        "\1\105\1\123\1\117\1\uffff\1\122\1\ufaff\1\117\2\105\1\115\1\111"+
        "\1\124\1\123\1\124\1\107\3\ufaff\1\44\1\125\1\115\1\ufaff\1\106"+
        "\1\105\1\uffff\2\ufaff\1\124\1\116\1\111\1\122\1\116\1\uffff\1\117"+
        "\1\ufaff\1\uffff\1\ufaff\1\uffff\1\111\1\124\1\ufaff\1\123\1\117"+
        "\1\ufaff\1\132\1\126\4\ufaff\1\116\1\ufaff\1\124\1\122\1\uffff\1"+
        "\101\1\111\1\ufaff\3\uffff\2\ufaff\2\uffff\1\123\1\uffff\1\124\1"+
        "\106\1\110\1\111\1\ufaff\1\116\1\104\1\123\1\111\1\105\1\uffff\1"+
        "\111\1\uffff\1\105\1\111\1\101\1\uffff\1\111\1\uffff\1\116\1\111"+
        "\1\124\1\uffff\1\105\1\uffff\1\101\1\124\1\131\2\105\1\124\2\ufaff"+
        "\1\107\1\ufaff\1\uffff\1\ufaff\1\uffff\1\124\1\ufaff\1\116\1\uffff"+
        "\1\101\1\uffff\1\111\1\104\1\uffff\1\107\1\115\1\124\1\104\1\110"+
        "\1\116\1\uffff\1\105\1\ufaff\1\113\1\115\2\ufaff\1\104\1\122\1\uffff"+
        "\1\124\1\uffff\1\122\1\103\1\ufaff\1\uffff\1\105\1\104\1\105\1\120"+
        "\1\105\2\ufaff\1\105\2\ufaff\2\111\1\uffff\1\122\1\116\1\103\2\116"+
        "\1\105\1\ufaff\2\105\3\uffff\1\ufaff\1\105\1\44\1\uffff\1\117\1"+
        "\122\1\uffff\1\ufaff\1\uffff\1\101\1\124\1\114\2\101\1\120\2\uffff"+
        "\1\117\1\ufaff\1\uffff\1\ufaff\1\122\1\uffff\2\105\4\uffff\1\ufaff"+
        "\1\uffff\1\106\1\111\1\114\1\117\3\uffff\1\105\1\ufaff\1\117\1\ufaff"+
        "\1\106\1\uffff\1\101\1\105\1\ufaff\1\116\1\104\1\102\1\ufaff\1\124"+
        "\1\123\1\115\1\103\1\102\1\117\1\ufaff\1\116\3\ufaff\1\116\1\ufaff"+
        "\2\uffff\1\ufaff\2\uffff\1\ufaff\1\122\1\uffff\1\103\1\ufaff\1\116"+
        "\2\ufaff\1\124\2\ufaff\1\111\1\105\1\112\1\131\1\uffff\1\ufaff\1"+
        "\105\2\uffff\1\125\1\126\1\111\1\ufaff\1\105\1\uffff\1\ufaff\1\127"+
        "\3\ufaff\2\uffff\1\ufaff\2\uffff\1\116\1\101\1\ufaff\2\124\1\ufaff"+
        "\1\125\1\ufaff\1\uffff\1\122\1\ufaff\1\uffff\1\44\1\ufaff\1\122"+
        "\1\ufaff\1\115\1\uffff\1\115\1\ufaff\1\105\1\122\1\124\1\105\1\116"+
        "\1\105\2\uffff\1\115\2\ufaff\1\uffff\1\117\1\124\1\ufaff\1\116\1"+
        "\103\1\uffff\1\122\1\uffff\1\111\1\114\1\104\1\uffff\2\ufaff\1\105"+
        "\1\uffff\3\105\1\124\1\125\1\122\1\uffff\1\ufaff\3\uffff\2\ufaff"+
        "\3\uffff\1\117\1\105\1\ufaff\1\uffff\1\124\2\uffff\1\101\2\uffff"+
        "\1\126\1\104\1\117\1\120\1\uffff\1\123\1\122\1\105\1\117\1\uffff"+
        "\1\ufaff\1\uffff\1\122\1\105\4\uffff\1\ufaff\1\114\1\uffff\1\ufaff"+
        "\1\111\1\ufaff\1\uffff\1\105\1\uffff\1\ufaff\1\uffff\1\ufaff\1\uffff"+
        "\1\115\1\uffff\2\120\1\uffff\1\ufaff\1\131\1\105\1\122\1\ufaff\1"+
        "\104\1\101\2\uffff\1\122\1\105\1\uffff\1\ufaff\1\124\1\115\1\105"+
        "\2\ufaff\2\uffff\1\ufaff\1\104\3\ufaff\1\124\1\131\3\uffff\1\120"+
        "\1\106\1\uffff\1\ufaff\1\102\1\105\1\ufaff\1\111\1\105\1\124\1\105"+
        "\1\ufaff\1\116\1\uffff\1\111\1\101\1\uffff\1\111\1\uffff\1\117\1"+
        "\uffff\1\ufaff\1\124\1\104\2\uffff\1\ufaff\1\114\1\ufaff\1\uffff"+
        "\1\ufaff\1\104\1\124\1\ufaff\1\uffff\1\ufaff\1\124\1\115\1\ufaff"+
        "\1\uffff\1\ufaff\1\101\1\104\3\uffff\2\ufaff\3\uffff\1\105\1\ufaff"+
        "\1\105\1\111\1\uffff\1\114\1\ufaff\1\uffff\1\116\1\ufaff\1\101\1"+
        "\ufaff\1\uffff\1\ufaff\1\124\1\104\1\132\1\116\1\uffff\1\101\1\ufaff"+
        "\1\uffff\1\105\2\uffff\1\ufaff\1\111\2\uffff\1\ufaff\1\101\2\uffff"+
        "\1\124\1\ufaff\2\uffff\1\ufaff\1\uffff\1\122\1\114\1\105\1\uffff"+
        "\1\ufaff\1\uffff\1\115\1\uffff\1\104\1\ufaff\1\uffff\3\105\1\ufaff"+
        "\1\124\1\uffff\1\ufaff\1\uffff\1\105\1\uffff\1\124\1\ufaff\2\uffff"+
        "\1\124\1\105\1\ufaff\1\uffff\1\120\1\ufaff\1\uffff\2\122\1\104\1"+
        "\uffff\1\125\1\uffff\1\123\1\ufaff\1\uffff\1\111\1\ufaff\1\uffff"+
        "\1\ufaff\1\uffff\3\ufaff\1\123\1\ufaff\1\uffff\1\105\5\uffff\1\ufaff"+
        "\1\uffff\1\123\1\uffff\1\ufaff\1\uffff";
    static final String DFA20_acceptS =
        "\31\uffff\1\u00b7\1\u00b8\1\u00b9\1\u00ba\1\u00bb\1\u00bc\1\u00bd"+
        "\1\u00be\1\u00bf\1\u00c0\1\u00c1\2\uffff\1\u00c7\1\u00c8\1\uffff"+
        "\1\u00ca\1\u00cb\1\u00cd\1\u00ce\1\u00cf\1\u00d0\1\u00d1\3\uffff"+
        "\1\u00d5\1\uffff\1\u00d6\1\u00d8\34\uffff\1\u00c2\1\6\75\uffff\1"+
        "\u00d2\1\uffff\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00d9\1\u00c9\6"+
        "\uffff\1\u00d4\1\uffff\1\u00d7\4\uffff\1\76\27\uffff\1\22\5\uffff"+
        "\1\5\3\uffff\1\36\1\153\15\uffff\1\u0090\1\10\1\uffff\1\62\25\uffff"+
        "\1\15\100\uffff\1\u00d3\23\uffff\1\u00b6\5\uffff\1\4\2\uffff\1\12"+
        "\1\uffff\1\3\1\uffff\1\155\4\uffff\1\152\15\uffff\1\103\12\uffff"+
        "\1\u0083\6\uffff\1\u00cc\25\uffff\1\173\1\uffff\1\u0098\16\uffff"+
        "\1\u0087\1\uffff\1\u00b3\20\uffff\1\125\4\uffff\1\115\24\uffff\1"+
        "\1\7\uffff\1\u0081\2\uffff\1\21\1\uffff\1\35\20\uffff\1\63\3\uffff"+
        "\1\u009a\1\57\1\u0089\2\uffff\1\7\1\33\1\uffff\1\123\12\uffff\1"+
        "\u0082\1\uffff\1\13\3\uffff\1\60\1\uffff\1\107\3\uffff\1\74\1\uffff"+
        "\1\u00a6\12\uffff\1\u0080\1\uffff\1\170\3\uffff\1\u00ae\1\uffff"+
        "\1\44\2\uffff\1\55\6\uffff\1\u0099\10\uffff\1\u008b\1\uffff\1\32"+
        "\3\uffff\1\u009f\14\uffff\1\45\11\uffff\1\154\1\177\1\135\3\uffff"+
        "\1\u008f\2\uffff\1\u00b0\1\uffff\1\41\6\uffff\1\u008d\1\2\2\uffff"+
        "\1\71\2\uffff\1\105\2\uffff\1\113\1\66\1\72\1\14\1\uffff\1\27\4"+
        "\uffff\1\50\1\172\1\137\5\uffff\1\134\24\uffff\1\u0096\1\16\1\uffff"+
        "\1\20\1\u009e\2\uffff\1\167\14\uffff\1\56\2\uffff\1\52\1\u00a1\5"+
        "\uffff\1\u00a0\5\uffff\1\u00a2\1\34\1\uffff\1\160\1\u008c\10\uffff"+
        "\1\u00a8\2\uffff\1\136\5\uffff\1\42\10\uffff\1\126\1\130\3\uffff"+
        "\1\u00b5\5\uffff\1\25\1\uffff\1\61\3\uffff\1\11\3\uffff\1\u008a"+
        "\6\uffff\1\106\1\uffff\1\104\1\u00a7\1\u00a4\2\uffff\1\151\1\17"+
        "\1\23\3\uffff\1\u0094\1\uffff\1\122\1\112\1\uffff\1\114\1\140\4"+
        "\uffff\1\u009b\4\uffff\1\46\1\uffff\1\117\2\uffff\1\75\1\161\1\u0097"+
        "\1\144\2\uffff\1\u00aa\3\uffff\1\70\1\uffff\1\64\1\uffff\1\67\1"+
        "\uffff\1\176\1\uffff\1\u00ab\2\uffff\1\101\7\uffff\1\u00a3\1\u00b1"+
        "\2\uffff\1\u00af\6\uffff\1\164\1\132\7\uffff\1\100\1\u00a5\1\124"+
        "\2\uffff\1\u0095\12\uffff\1\156\2\uffff\1\u0084\1\uffff\1\77\1\uffff"+
        "\1\157\3\uffff\1\53\1\175\3\uffff\1\143\4\uffff\1\163\4\uffff\1"+
        "\147\3\uffff\1\65\1\165\1\73\2\uffff\1\u0091\1\110\1\24\4\uffff"+
        "\1\102\2\uffff\1\u009d\4\uffff\1\31\5\uffff\1\u00a9\2\uffff\1\51"+
        "\1\uffff\1\111\1\162\2\uffff\1\43\1\166\2\uffff\1\26\1\u008e\2\uffff"+
        "\1\127\1\u0092\1\uffff\1\47\3\uffff\1\u00b2\1\uffff\1\116\1\uffff"+
        "\1\u009c\2\uffff\1\37\5\uffff\1\121\1\uffff\1\131\1\uffff\1\141"+
        "\2\uffff\1\u00b4\1\54\3\uffff\1\30\2\uffff\1\40\3\uffff\1\133\1"+
        "\uffff\1\150\2\uffff\1\145\2\uffff\1\u0085\1\uffff\1\120\5\uffff"+
        "\1\146\1\uffff\1\142\1\u0088\1\u00ad\1\u00ac\1\u0093\1\uffff\1\174"+
        "\1\uffff\1\u0086\1\uffff\1\171";
    static final String DFA20_specialS =
        "\u03e4\uffff}>";
    static final String[] DFA20_transitionS = {
            "\2\66\2\uffff\1\66\22\uffff\1\66\1\6\1\61\1\uffff\1\27\1\52"+
            "\1\53\1\60\1\35\1\36\1\51\1\47\1\33\1\50\1\31\1\46\1\62\11\63"+
            "\1\32\1\34\1\44\1\43\1\45\1\57\1\uffff\1\3\1\13\1\25\1\12\1"+
            "\11\1\2\1\14\1\15\1\10\1\22\1\26\1\7\1\24\1\5\1\4\1\21\1\65"+
            "\1\23\1\17\1\1\1\20\1\30\1\16\3\65\1\37\1\uffff\1\40\1\56\1"+
            "\64\33\65\1\41\1\55\1\42\1\54\101\uffff\27\65\1\uffff\37\65"+
            "\1\uffff\u1f08\65\u1040\uffff\u0150\65\u0170\uffff\u0080\65"+
            "\u0080\uffff\u092e\65\u10d2\uffff\u5200\65\u5900\uffff\u0200"+
            "\65",
            "\1\71\1\75\2\uffff\1\73\2\uffff\1\74\1\72\5\uffff\1\70\2\uffff"+
            "\1\67",
            "\1\100\3\uffff\1\76\3\uffff\1\103\2\uffff\1\104\2\uffff\1\102"+
            "\2\uffff\1\77\2\uffff\1\101",
            "\1\112\1\uffff\1\111\5\uffff\1\110\1\uffff\1\105\3\uffff\1\106"+
            "\1\107",
            "\1\120\7\uffff\1\117\1\uffff\1\114\1\uffff\1\113\2\uffff\1\115"+
            "\1\116",
            "\1\121\5\uffff\1\122",
            "\1\123",
            "\1\125\3\uffff\1\130\3\uffff\1\127\5\uffff\1\126",
            "\1\133\1\uffff\1\132\7\uffff\1\131\4\uffff\1\134\1\135",
            "\1\140\1\uffff\1\141\4\uffff\1\137\4\uffff\1\136",
            "\1\143\3\uffff\1\142\3\uffff\1\144\5\uffff\1\146\2\uffff\1\145",
            "\1\151\3\uffff\1\150\5\uffff\1\147\5\uffff\1\153\3\uffff\1\152",
            "\1\154",
            "\1\155",
            "\1\156\1\157",
            "\1\161\1\uffff\1\160\2\uffff\1\163\4\uffff\1\164\1\uffff\1\165"+
            "\3\uffff\1\162\1\166",
            "\1\167\4\uffff\1\171\1\170",
            "\1\175\12\uffff\1\174\5\uffff\1\173\2\uffff\1\172",
            "\1\176",
            "\1\u0080\1\uffff\1\u0083\1\uffff\1\177\3\uffff\1\u0081\2\uffff"+
            "\1\u0084\2\uffff\1\u0082",
            "\1\u0086\7\uffff\1\u0085\11\uffff\1\u0087",
            "\1\u008d\6\uffff\1\u008c\3\uffff\1\u008b\2\uffff\1\u0089\2\uffff"+
            "\1\u008a\2\uffff\1\u0088",
            "\1\u008e",
            "\1\65\13\uffff\12\65\7\uffff\4\65\1\u0091\5\65\1\u008f\12\65"+
            "\1\u0090\4\65\4\uffff\1\65\1\uffff\32\65\105\uffff\27\65\1\uffff"+
            "\37\65\1\uffff\u1f08\65\u1040\uffff\u0150\65\u0170\uffff\u0080"+
            "\65\u0080\uffff\u092e\65\u10d2\uffff\u5200\65\u5900\uffff\u0200"+
            "\65",
            "\1\u0093",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0094\1\123",
            "\1\u0096",
            "",
            "",
            "\1\u0098",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\47\u009a\1\u009c\64\u009a\1\u009b\uffa2\u009a",
            "\42\u009d\1\u009f\71\u009d\1\u009e\uffa2\u009d",
            "\1\u00a0",
            "",
            "\2\u00a2\1\uffff\12\u00a1\1\u00a2\6\uffff\32\u00a1\4\uffff\1"+
            "\u00a1\1\uffff\32\u00a1",
            "",
            "",
            "\1\u00a3\7\uffff\1\u00a4\13\uffff\1\u00a5",
            "\1\65\13\uffff\12\65\7\uffff\24\65\1\u00a6\5\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u00a8",
            "\1\u00a9\1\u00aa",
            "\1\u00ac\4\uffff\1\u00ad\5\uffff\1\u00ab",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3\1\uffff\1\u00b4",
            "\1\u00b5",
            "\1\u00b7\6\uffff\1\u00b8\5\uffff\1\u00b6",
            "\1\u00b9",
            "\1\u00ba\2\uffff\1\u00bb",
            "\1\u00bc\16\uffff\1\u00bd",
            "\1\65\13\uffff\12\65\7\uffff\2\65\1\u00be\27\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u00c1\7\uffff\1\u00c0",
            "\1\u00c2",
            "\1\u00c3",
            "\1\65\13\uffff\12\65\7\uffff\3\65\1\u00c4\26\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u00c6",
            "\1\u00c7",
            "\1\u00c8",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u00cb",
            "\1\u00cc",
            "",
            "",
            "\1\u00cd",
            "\1\u00cf\1\uffff\1\u00ce\12\uffff\1\u00d0",
            "\1\u00d3\1\uffff\1\u00d1\1\u00d2",
            "\1\u00d4",
            "\1\65\13\uffff\12\65\7\uffff\17\65\1\u00d7\2\65\1\u00d6\1\u00d5"+
            "\6\65\4\uffff\1\65\1\uffff\32\65\105\uffff\27\65\1\uffff\37"+
            "\65\1\uffff\u1f08\65\u1040\uffff\u0150\65\u0170\uffff\u0080"+
            "\65\u0080\uffff\u092e\65\u10d2\uffff\u5200\65\u5900\uffff\u0200"+
            "\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u00da",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u00dc",
            "\1\u00de\6\uffff\1\u00df\3\uffff\1\u00dd",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e4\6\uffff\1\u00e3",
            "\1\u00e5",
            "\1\u00e8\1\u00e6\2\uffff\1\u00e7",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00ec\4\uffff\1\u00eb",
            "\1\u00ed\6\uffff\1\u00ee",
            "\1\u00ef\15\uffff\1\u00f0",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u00f2",
            "\1\u00f3\15\uffff\1\u00f4",
            "\1\u00f5",
            "\1\u00f6\3\uffff\1\u00f7",
            "\1\u00f8",
            "\1\u00f9\1\u00fc\3\uffff\1\u00fb\1\u00fa\1\uffff\1\u00fd",
            "\1\u00fe",
            "\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0104\2\uffff\1\u0103",
            "\1\u0105\2\uffff\1\u0107\4\uffff\1\u0108\2\uffff\1\u0109\6\uffff"+
            "\1\u0106",
            "\1\u010a",
            "\1\u010c\3\uffff\1\u010b",
            "\1\u010d",
            "\1\u010f\11\uffff\1\u010e",
            "\1\u0110",
            "\1\u0111",
            "\1\u0112",
            "\1\u0114\1\uffff\1\u0116\1\u0115\2\uffff\1\u0118\6\uffff\1\u0117"+
            "\1\uffff\1\u0113\5\uffff\1\u0119",
            "\1\u011a",
            "\1\u011b",
            "\1\u011c",
            "\1\u011d",
            "\1\u011e",
            "\1\u011f",
            "\1\u0120\3\uffff\1\u0121",
            "\1\u0122",
            "\1\u0123",
            "\1\u0125\1\u0124\1\u0126",
            "\1\u0127\11\uffff\1\u0128",
            "\1\u0129",
            "\1\u012a",
            "\1\u012b",
            "\1\u012c",
            "\1\u012d",
            "\1\u012e",
            "\1\u012f",
            "",
            "\1\u0130",
            "",
            "",
            "",
            "",
            "",
            "",
            "\47\u009a\1\u009c\64\u009a\1\u009b\uffa2\u009a",
            "\uffff\u0131",
            "\1\61\4\uffff\1\60",
            "\42\u009d\1\u009f\71\u009d\1\u009e\uffa2\u009d",
            "\uffff\u0133",
            "\1\61\4\uffff\1\60",
            "",
            "\2\u00a2\1\uffff\12\u00a1\1\u00a2\6\uffff\32\u00a1\4\uffff\1"+
            "\u00a1\1\uffff\32\u00a1",
            "",
            "\1\u0134",
            "\1\u0135",
            "\1\u0136",
            "\1\u0137",
            "",
            "\1\u0138",
            "\1\u0139",
            "\1\u013a",
            "\1\u013b",
            "\1\u013c",
            "\1\u013d",
            "\1\u013e",
            "\1\u013f",
            "\1\u0140",
            "\1\u0141",
            "\1\u0142",
            "\1\u0143",
            "\1\u0144",
            "\1\65\13\uffff\12\65\7\uffff\14\65\1\u0145\15\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u0147",
            "\1\u0148",
            "\1\u0149",
            "\1\u014a",
            "\1\u014b",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u014d",
            "\1\u014e",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0150",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0152",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0154",
            "",
            "\1\u0155",
            "\1\65\13\uffff\12\65\7\uffff\4\65\1\u0156\12\65\1\u0157\12\65"+
            "\4\uffff\1\65\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1"+
            "\uffff\u1f08\65\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080"+
            "\uffff\u092e\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u0159",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u015a",
            "\1\u015b",
            "\1\u015c\11\uffff\1\u015d",
            "\1\u015e",
            "\1\u015f",
            "\1\u0160",
            "\1\u0161",
            "\1\u0162",
            "\1\u0163",
            "\1\65\13\uffff\12\65\7\uffff\4\65\1\u0164\11\65\1\u0165\13\65"+
            "\4\uffff\1\65\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1"+
            "\uffff\u1f08\65\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080"+
            "\uffff\u092e\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u0167",
            "\1\u0169\23\uffff\1\u0168",
            "",
            "",
            "\1\u016a",
            "",
            "\1\u016b",
            "\1\u016c",
            "\1\u016d",
            "\1\u016e",
            "\1\u016f",
            "\1\u0170",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0172",
            "\1\u0173\3\uffff\1\u0174",
            "\1\u0175\3\uffff\1\u0176",
            "\1\u0177",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0179",
            "\1\u017a",
            "\1\u017b",
            "\1\u017c",
            "\1\u017d",
            "\1\u017e",
            "\1\u017f",
            "\1\u0180",
            "\1\u0181",
            "",
            "\1\u0182",
            "\1\u0183",
            "\1\u0184",
            "\1\u0185",
            "\1\u0187\3\uffff\1\u0186",
            "\1\u0188",
            "\1\u0189",
            "\1\u018a",
            "\1\u018b",
            "\1\u018c",
            "\1\u018d",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u018f",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0191",
            "\1\u0192",
            "\1\u0193",
            "\1\u0195\3\uffff\1\u0194\13\uffff\1\u0196",
            "\1\u0197",
            "\1\u0198",
            "\1\u0199",
            "\1\u019a",
            "\1\u019c\1\uffff\1\u019b",
            "\1\u019d",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\u019e\1\uffff\32"+
            "\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01a0",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01a2",
            "\1\u01a3",
            "\1\u01a4",
            "\1\u01a5",
            "\1\u01a6",
            "\1\u01a7",
            "\1\u01a8\12\uffff\1\u01a9",
            "\1\u01aa",
            "\1\u01ab",
            "\1\u01ac",
            "\1\u01ad",
            "\1\u01ae",
            "\1\u01af",
            "\1\u01b0",
            "\1\u01b1",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01b3",
            "\1\u01b4",
            "\1\u01b5",
            "\1\65\13\uffff\12\65\7\uffff\11\65\1\u01b6\20\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u01b8",
            "\1\u01b9",
            "\1\u01ba",
            "\1\u01bb",
            "\1\u01bc\10\uffff\1\u01bd",
            "\1\u01be",
            "\1\u01bf",
            "\1\u01c0",
            "\1\u01c1",
            "\1\u01c2",
            "\1\u01c4\16\uffff\1\u01c3",
            "\1\u01c5",
            "\1\u01c6",
            "\1\u01c7",
            "\1\u01c8",
            "\1\u01c9",
            "\47\u009a\1\u009c\64\u009a\1\u009b\uffa2\u009a",
            "",
            "\42\u009d\1\u009f\71\u009d\1\u009e\uffa2\u009d",
            "\1\u01ca",
            "\1\u01cb",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01cd",
            "\1\u01ce",
            "\1\u01cf",
            "\1\u01d0",
            "\1\u01d1",
            "\1\u01d2",
            "\1\u01d3",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01d5",
            "\1\u01d6",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01d8",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01da",
            "\1\u01db",
            "",
            "\1\u01dc",
            "\1\u01dd",
            "\1\u01de",
            "\1\u01df",
            "\1\u01e0",
            "",
            "\1\u01e1",
            "\1\u01e2",
            "",
            "\1\u01e3",
            "",
            "\1\u01e4",
            "",
            "\1\u01e5",
            "\1\u01e6",
            "\1\u01e7",
            "\1\u01e8",
            "",
            "\1\u01e9",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01eb",
            "\1\u01ed\7\uffff\1\u01ec",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01f1",
            "\1\u01f2",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u01f5",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u01f7",
            "\1\u01f8",
            "\1\u01f9",
            "\1\u01fa",
            "\1\u01fb",
            "\1\u01fd\3\uffff\1\u01fc",
            "\1\u01fe",
            "\1\u01ff",
            "\1\u0200",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\21\65\1\u0202\10\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u0204",
            "\1\u0205",
            "\1\65\13\uffff\12\65\7\uffff\1\65\1\u0206\30\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\23\65\1\u0208\6\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u020a\10\uffff\1\u020b",
            "",
            "\1\u020c",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u020e",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0210",
            "\1\u0211",
            "\1\u0212",
            "\1\u0213",
            "\1\u0214",
            "\1\u0215",
            "\1\u0216",
            "\1\u0217",
            "\1\u0218",
            "\1\u0219",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u021b",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u021d",
            "\1\u021e",
            "\1\u021f",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0221",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0223",
            "\1\65\13\uffff\12\65\7\uffff\4\65\1\u0224\25\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u0226",
            "\1\u0227",
            "\1\u0228",
            "\1\u0229",
            "\1\u022a",
            "\1\u022b",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u022d",
            "\1\u022e",
            "\1\u022f",
            "\1\u0230",
            "",
            "\1\u0231",
            "",
            "\1\u0232",
            "\1\u0233",
            "\1\u0234",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0236",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0238",
            "\1\u0239",
            "\1\65\13\uffff\12\65\7\uffff\22\65\1\u023a\7\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u023c",
            "\1\u023d",
            "\1\u023e",
            "\1\u023f",
            "\1\u0240",
            "\1\u0241",
            "\1\u0242",
            "",
            "\1\u0243",
            "\1\u0244",
            "\1\u0245",
            "\1\u0246",
            "",
            "\1\u0247",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0249",
            "\1\u024a",
            "\1\u024b",
            "\1\u024c",
            "\1\u024d",
            "\1\u024e",
            "\1\u024f",
            "\1\u0250",
            "\1\u0251",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0255",
            "\1\u0256",
            "\1\u0257",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0259",
            "\1\u025a",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\22\65\1\u025c\7\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u025e",
            "\1\u025f",
            "\1\u0260",
            "\1\u0261",
            "\1\u0262",
            "",
            "\1\u0263",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0266",
            "\1\u0267",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0269",
            "\1\u026a",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u026c",
            "\1\u026d",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0272",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0274",
            "\1\u0275",
            "",
            "\1\u0276",
            "\1\u0277",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\u027b",
            "",
            "\1\u027c",
            "\1\u027d",
            "\1\u027e",
            "\1\u027f",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0281",
            "\1\u0282",
            "\1\u0283",
            "\1\u0284",
            "\1\u0285",
            "",
            "\1\u0286",
            "",
            "\1\u0287",
            "\1\u0288",
            "\1\u0289",
            "",
            "\1\u028a",
            "",
            "\1\u028b",
            "\1\u028c",
            "\1\u028d",
            "",
            "\1\u028e",
            "",
            "\1\u028f",
            "\1\u0290",
            "\1\u0291",
            "\1\u0292",
            "\1\u0293",
            "\1\u0294",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0297",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u029a",
            "\1\65\13\uffff\12\65\7\uffff\17\65\1\u029b\12\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u029d",
            "",
            "\1\u029e",
            "",
            "\1\u029f",
            "\1\u02a0",
            "",
            "\1\u02a1",
            "\1\u02a2",
            "\1\u02a3",
            "\1\u02a4",
            "\1\u02a5",
            "\1\u02a6",
            "",
            "\1\u02a7",
            "\1\65\13\uffff\12\65\7\uffff\23\65\1\u02a8\6\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u02aa",
            "\1\u02ab",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02ae",
            "\1\u02af",
            "",
            "\1\u02b0",
            "",
            "\1\u02b1",
            "\1\u02b2",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u02b4",
            "\1\u02b5",
            "\1\u02b6",
            "\1\u02b7",
            "\1\u02b8",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02bb",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02be",
            "\1\u02bf",
            "",
            "\1\u02c0",
            "\1\u02c1",
            "\1\u02c2",
            "\1\u02c3",
            "\1\u02c4",
            "\1\u02c5",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02c7",
            "\1\u02c8",
            "",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02ca",
            "\1\u02cb",
            "",
            "\1\u02cc",
            "\1\u02cd",
            "",
            "\1\65\13\uffff\12\65\7\uffff\1\u02ce\31\65\4\uffff\1\65\1\uffff"+
            "\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040"+
            "\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2"+
            "\uffff\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u02d0",
            "\1\u02d1",
            "\1\u02d2",
            "\1\u02d3",
            "\1\u02d4",
            "\1\u02d5",
            "",
            "",
            "\1\u02d6",
            "\1\65\13\uffff\12\65\7\uffff\23\65\1\u02d7\6\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02da",
            "",
            "\1\u02db",
            "\1\u02dc",
            "",
            "",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u02de",
            "\1\u02df",
            "\1\u02e0",
            "\1\u02e1",
            "",
            "",
            "",
            "\1\u02e2",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02e4",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02e6",
            "",
            "\1\u02e7",
            "\1\u02e8",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02ea",
            "\1\u02eb",
            "\1\u02ec",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02ee",
            "\1\u02ef",
            "\1\u02f0",
            "\1\u02f1",
            "\1\u02f2",
            "\1\u02f3",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02f5",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02f9",
            "\1\65\13\uffff\12\65\7\uffff\22\65\1\u02fa\7\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u02fe",
            "",
            "\1\u02ff",
            "\1\65\13\uffff\12\65\7\uffff\22\65\1\u0300\7\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u0302",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0305",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0308",
            "\1\u0309",
            "\1\u030a",
            "\1\u030b",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u030d",
            "",
            "",
            "\1\u030e",
            "\1\u030f",
            "\1\u0310",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0312",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0315\4\uffff\1\u0314",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\u031a",
            "\1\u031b",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u031d",
            "\1\u031e",
            "\1\65\13\uffff\12\65\7\uffff\22\65\1\u031f\7\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u0321",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0323",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0325",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0327",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0329",
            "",
            "\1\u032a",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u032c",
            "\1\u032d",
            "\1\u032e",
            "\1\u032f",
            "\1\u0330",
            "\1\u0331",
            "",
            "",
            "\1\u0332",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0335",
            "\1\u0336",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0338",
            "\1\u0339",
            "",
            "\1\u033a",
            "",
            "\1\u033b",
            "\1\u033c",
            "\1\u033d",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0340",
            "",
            "\1\u0341",
            "\1\u0342",
            "\1\u0343",
            "\1\u0344",
            "\1\u0345",
            "\1\u0346",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "",
            "\1\u034a",
            "\1\u034b",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u034d",
            "",
            "",
            "\1\u034e",
            "",
            "",
            "\1\u034f",
            "\1\u0350",
            "\1\u0351",
            "\1\u0352",
            "",
            "\1\u0353",
            "\1\u0354",
            "\1\u0355",
            "\1\u0356",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0358",
            "\1\u0359",
            "",
            "",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u035b",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u035d",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u035f",
            "",
            "\1\65\13\uffff\12\65\7\uffff\4\65\1\u0361\15\65\1\u0360\7\65"+
            "\4\uffff\1\65\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1"+
            "\uffff\u1f08\65\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080"+
            "\uffff\u092e\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u0364",
            "",
            "\1\u0365",
            "\1\u0366",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0368",
            "\1\u0369",
            "\1\u036a",
            "\1\65\13\uffff\12\65\7\uffff\22\65\1\u036b\7\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u036d",
            "\1\u036e",
            "",
            "",
            "\1\u036f",
            "\1\u0370",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0372",
            "\1\u0373",
            "\1\u0374",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0378",
            "\1\65\13\uffff\12\65\7\uffff\22\65\1\u0379\7\65\4\uffff\1\65"+
            "\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65"+
            "\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e"+
            "\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u037d",
            "\1\u037e",
            "",
            "",
            "",
            "\1\u037f",
            "\1\u0380",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0382",
            "\1\u0383",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0385",
            "\1\u0386",
            "\1\u0387",
            "\1\u0388",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u038a",
            "",
            "\1\u038b",
            "\1\u038c",
            "",
            "\1\u038d",
            "",
            "\1\u038e",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0390",
            "\1\u0391",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0393",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u0396",
            "\1\u0397",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u039a",
            "\1\u039b",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u039e",
            "\1\u039f",
            "",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "",
            "\1\u03a2",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u03a4",
            "\1\u03a5",
            "",
            "\1\u03a6",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03a8",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u03aa",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\4\65\1\u03ac\15\65\1\u03ad\7\65"+
            "\4\uffff\1\65\1\uffff\32\65\105\uffff\27\65\1\uffff\37\65\1"+
            "\uffff\u1f08\65\u1040\uffff\u0150\65\u0170\uffff\u0080\65\u0080"+
            "\uffff\u092e\65\u10d2\uffff\u5200\65\u5900\uffff\u0200\65",
            "\1\u03af",
            "\1\u03b0",
            "\1\u03b1",
            "\1\u03b2",
            "",
            "\1\u03b3",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03b5",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u03b7",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u03b9",
            "",
            "",
            "\1\u03ba",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03bd",
            "\1\u03be",
            "\1\u03bf",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03c1",
            "",
            "\1\u03c2",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03c4",
            "\1\u03c5",
            "\1\u03c6",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u03c8",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03ca",
            "",
            "\1\u03cb",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "",
            "\1\u03cd",
            "\1\u03ce",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03d0",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03d2",
            "\1\u03d3",
            "\1\u03d4",
            "",
            "\1\u03d5",
            "",
            "\1\u03d6",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03d8",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "\1\u03de",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03e0",
            "",
            "",
            "",
            "",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            "",
            "\1\u03e2",
            "",
            "\1\65\13\uffff\12\65\7\uffff\32\65\4\uffff\1\65\1\uffff\32\65"+
            "\105\uffff\27\65\1\uffff\37\65\1\uffff\u1f08\65\u1040\uffff"+
            "\u0150\65\u0170\uffff\u0080\65\u0080\uffff\u092e\65\u10d2\uffff"+
            "\u5200\65\u5900\uffff\u0200\65",
            ""
    };

    static final short[] DFA20_eot = DFA.unpackEncodedString(DFA20_eotS);
    static final short[] DFA20_eof = DFA.unpackEncodedString(DFA20_eofS);
    static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars(DFA20_minS);
    static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars(DFA20_maxS);
    static final short[] DFA20_accept = DFA.unpackEncodedString(DFA20_acceptS);
    static final short[] DFA20_special = DFA.unpackEncodedString(DFA20_specialS);
    static final short[][] DFA20_transition;

    static {
        int numStates = DFA20_transitionS.length;
        DFA20_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
        }
    }

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = DFA20_eot;
            this.eof = DFA20_eof;
            this.min = DFA20_min;
            this.max = DFA20_max;
            this.accept = DFA20_accept;
            this.special = DFA20_special;
            this.transition = DFA20_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( KW_TRUE | KW_FALSE | KW_ALL | KW_AND | KW_OR | KW_NOT | KW_LIKE | KW_IF | KW_EXISTS | KW_ASC | KW_DESC | KW_ORDER | KW_BY | KW_GROUP | KW_HAVING | KW_WHERE | KW_FROM | KW_AS | KW_SELECT | KW_DISTINCT | KW_INSERT | KW_OVERWRITE | KW_OUTER | KW_UNIQUEJOIN | KW_PRESERVE | KW_JOIN | KW_LEFT | KW_RIGHT | KW_FULL | KW_ON | KW_PARTITION | KW_PARTITIONS | KW_TABLE | KW_TABLES | KW_FUNCTIONS | KW_SHOW | KW_MSCK | KW_REPAIR | KW_DIRECTORY | KW_LOCAL | KW_TRANSFORM | KW_USING | KW_CLUSTER | KW_DISTRIBUTE | KW_SORT | KW_UNION | KW_LOAD | KW_DATA | KW_INPATH | KW_IS | KW_NULL | KW_CREATE | KW_EXTERNAL | KW_ALTER | KW_CHANGE | KW_COLUMN | KW_FIRST | KW_AFTER | KW_DESCRIBE | KW_DROP | KW_RENAME | KW_TO | KW_COMMENT | KW_BOOLEAN | KW_TINYINT | KW_SMALLINT | KW_INT | KW_BIGINT | KW_FLOAT | KW_DOUBLE | KW_DATE | KW_DATETIME | KW_TIMESTAMP | KW_STRING | KW_ARRAY | KW_STRUCT | KW_MAP | KW_UNIONTYPE | KW_REDUCE | KW_PARTITIONED | KW_CLUSTERED | KW_SORTED | KW_INTO | KW_BUCKETS | KW_ROW | KW_FORMAT | KW_DELIMITED | KW_FIELDS | KW_TERMINATED | KW_ESCAPED | KW_COLLECTION | KW_ITEMS | KW_KEYS | KW_KEY_TYPE | KW_LINES | KW_STORED | KW_FILEFORMAT | KW_SEQUENCEFILE | KW_TEXTFILE | KW_RCFILE | KW_INPUTFORMAT | KW_OUTPUTFORMAT | KW_LOCATION | KW_TABLESAMPLE | KW_BUCKET | KW_OUT | KW_OF | KW_CAST | KW_ADD | KW_REPLACE | KW_COLUMNS | KW_RLIKE | KW_REGEXP | KW_TEMPORARY | KW_FUNCTION | KW_EXPLAIN | KW_EXTENDED | KW_FORMATTED | KW_SERDE | KW_WITH | KW_SERDEPROPERTIES | KW_LIMIT | KW_SET | KW_TBLPROPERTIES | KW_VALUE_TYPE | KW_ELEM_TYPE | KW_CASE | KW_WHEN | KW_THEN | KW_ELSE | KW_END | KW_MAPJOIN | KW_STREAMTABLE | KW_CLUSTERSTATUS | KW_UTC | KW_UTCTIMESTAMP | KW_LONG | KW_DELETE | KW_PLUS | KW_MINUS | KW_FETCH | KW_INTERSECT | KW_VIEW | KW_IN | KW_DATABASE | KW_DATABASES | KW_MATERIALIZED | KW_SCHEMA | KW_SCHEMAS | KW_GRANT | KW_REVOKE | KW_SSL | KW_UNDO | KW_LOCK | KW_UNLOCK | KW_PROCEDURE | KW_UNSIGNED | KW_WHILE | KW_READ | KW_READS | KW_PURGE | KW_RANGE | KW_ANALYZE | KW_BEFORE | KW_BETWEEN | KW_BOTH | KW_BINARY | KW_CROSS | KW_CONTINUE | KW_CURSOR | KW_TRIGGER | KW_RECORDREADER | KW_RECORDWRITER | KW_SEMI | KW_LATERAL | KW_TOUCH | KW_ARCHIVE | KW_UNARCHIVE | KW_USE | KW_IDENTIFIED | KW_OPTION | KW_FOR | DOT | COLON | COMMA | SEMICOLON | LPAREN | RPAREN | LSQUARE | RSQUARE | LCURLY | RCURLY | EQUAL | NOTEQUAL | LESSTHANOREQUALTO | LESSTHAN | GREATERTHANOREQUALTO | GREATERTHAN | DIVIDE | PLUS | MINUS | STAR | MOD | DIV | AMPERSAND | TILDE | BITWISEOR | BITWISEXOR | QUESTION | DOLLAR | StringLiteral | CharSetLiteral | Number | Identifier | CharSetName | WS | COMMENT );";
        }
    }
 

}