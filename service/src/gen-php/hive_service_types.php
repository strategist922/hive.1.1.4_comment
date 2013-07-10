<?php
/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
include_once $GLOBALS['THRIFT_ROOT'].'/Thrift.php';

include_once $GLOBALS['THRIFT_ROOT'].'/packages/fb303/fb303_types.php';
include_once $GLOBALS['THRIFT_ROOT'].'/packages/hive_metastore/hive_metastore_types.php';
include_once $GLOBALS['THRIFT_ROOT'].'/packages/queryplan/queryplan_types.php';

$GLOBALS['E_JobTrackerState'] = array(
  'INITIALIZING' => 1,
  'RUNNING' => 2,
);

final class JobTrackerState {
  const INITIALIZING = 1;
  const RUNNING = 2;
  static public $__names = array(
    1 => 'INITIALIZING',
    2 => 'RUNNING',
  );
}

class HiveClusterStatus {
  static $_TSPEC;

  public $taskTrackers = null;
  public $mapTasks = null;
  public $reduceTasks = null;
  public $maxMapTasks = null;
  public $maxReduceTasks = null;
  public $state = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'taskTrackers',
          'type' => TType::I32,
          ),
        2 => array(
          'var' => 'mapTasks',
          'type' => TType::I32,
          ),
        3 => array(
          'var' => 'reduceTasks',
          'type' => TType::I32,
          ),
        4 => array(
          'var' => 'maxMapTasks',
          'type' => TType::I32,
          ),
        5 => array(
          'var' => 'maxReduceTasks',
          'type' => TType::I32,
          ),
        6 => array(
          'var' => 'state',
          'type' => TType::I32,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['taskTrackers'])) {
        $this->taskTrackers = $vals['taskTrackers'];
      }
      if (isset($vals['mapTasks'])) {
        $this->mapTasks = $vals['mapTasks'];
      }
      if (isset($vals['reduceTasks'])) {
        $this->reduceTasks = $vals['reduceTasks'];
      }
      if (isset($vals['maxMapTasks'])) {
        $this->maxMapTasks = $vals['maxMapTasks'];
      }
      if (isset($vals['maxReduceTasks'])) {
        $this->maxReduceTasks = $vals['maxReduceTasks'];
      }
      if (isset($vals['state'])) {
        $this->state = $vals['state'];
      }
    }
  }

  public function getName() {
    return 'HiveClusterStatus';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->taskTrackers);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->mapTasks);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->reduceTasks);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->maxMapTasks);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 5:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->maxReduceTasks);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 6:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->state);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('HiveClusterStatus');
    if ($this->taskTrackers !== null) {
      $xfer += $output->writeFieldBegin('taskTrackers', TType::I32, 1);
      $xfer += $output->writeI32($this->taskTrackers);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->mapTasks !== null) {
      $xfer += $output->writeFieldBegin('mapTasks', TType::I32, 2);
      $xfer += $output->writeI32($this->mapTasks);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->reduceTasks !== null) {
      $xfer += $output->writeFieldBegin('reduceTasks', TType::I32, 3);
      $xfer += $output->writeI32($this->reduceTasks);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->maxMapTasks !== null) {
      $xfer += $output->writeFieldBegin('maxMapTasks', TType::I32, 4);
      $xfer += $output->writeI32($this->maxMapTasks);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->maxReduceTasks !== null) {
      $xfer += $output->writeFieldBegin('maxReduceTasks', TType::I32, 5);
      $xfer += $output->writeI32($this->maxReduceTasks);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->state !== null) {
      $xfer += $output->writeFieldBegin('state', TType::I32, 6);
      $xfer += $output->writeI32($this->state);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class TTaskInfo {
  static $_TSPEC;

  public $infoMap = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'infoMap',
          'type' => TType::MAP,
          'ktype' => TType::STRING,
          'vtype' => TType::STRING,
          'key' => array(
            'type' => TType::STRING,
          ),
          'val' => array(
            'type' => TType::STRING,
            ),
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['infoMap'])) {
        $this->infoMap = $vals['infoMap'];
      }
    }
  }

  public function getName() {
    return 'TTaskInfo';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::MAP) {
            $this->infoMap = array();
            $_size0 = 0;
            $_ktype1 = 0;
            $_vtype2 = 0;
            $xfer += $input->readMapBegin($_ktype1, $_vtype2, $_size0);
            for ($_i4 = 0; $_i4 < $_size0; ++$_i4)
            {
              $key5 = '';
              $val6 = '';
              $xfer += $input->readString($key5);
              $xfer += $input->readString($val6);
              $this->infoMap[$key5] = $val6;
            }
            $xfer += $input->readMapEnd();
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('TTaskInfo');
    if ($this->infoMap !== null) {
      if (!is_array($this->infoMap)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('infoMap', TType::MAP, 1);
      {
        $output->writeMapBegin(TType::STRING, TType::STRING, count($this->infoMap));
        {
          foreach ($this->infoMap as $kiter7 => $viter8)
          {
            $xfer += $output->writeString($kiter7);
            $xfer += $output->writeString($viter8);
          }
        }
        $output->writeMapEnd();
      }
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class TQueryInfo {
  static $_TSPEC;

  public $taskInfos = null;
  public $queryInfos = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'taskInfos',
          'type' => TType::MAP,
          'ktype' => TType::STRING,
          'vtype' => TType::STRUCT,
          'key' => array(
            'type' => TType::STRING,
          ),
          'val' => array(
            'type' => TType::STRUCT,
            'class' => 'TTaskInfo',
            ),
          ),
        2 => array(
          'var' => 'queryInfos',
          'type' => TType::MAP,
          'ktype' => TType::STRING,
          'vtype' => TType::STRING,
          'key' => array(
            'type' => TType::STRING,
          ),
          'val' => array(
            'type' => TType::STRING,
            ),
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['taskInfos'])) {
        $this->taskInfos = $vals['taskInfos'];
      }
      if (isset($vals['queryInfos'])) {
        $this->queryInfos = $vals['queryInfos'];
      }
    }
  }

  public function getName() {
    return 'TQueryInfo';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::MAP) {
            $this->taskInfos = array();
            $_size9 = 0;
            $_ktype10 = 0;
            $_vtype11 = 0;
            $xfer += $input->readMapBegin($_ktype10, $_vtype11, $_size9);
            for ($_i13 = 0; $_i13 < $_size9; ++$_i13)
            {
              $key14 = '';
              $val15 = new TTaskInfo();
              $xfer += $input->readString($key14);
              $val15 = new TTaskInfo();
              $xfer += $val15->read($input);
              $this->taskInfos[$key14] = $val15;
            }
            $xfer += $input->readMapEnd();
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::MAP) {
            $this->queryInfos = array();
            $_size16 = 0;
            $_ktype17 = 0;
            $_vtype18 = 0;
            $xfer += $input->readMapBegin($_ktype17, $_vtype18, $_size16);
            for ($_i20 = 0; $_i20 < $_size16; ++$_i20)
            {
              $key21 = '';
              $val22 = '';
              $xfer += $input->readString($key21);
              $xfer += $input->readString($val22);
              $this->queryInfos[$key21] = $val22;
            }
            $xfer += $input->readMapEnd();
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('TQueryInfo');
    if ($this->taskInfos !== null) {
      if (!is_array($this->taskInfos)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('taskInfos', TType::MAP, 1);
      {
        $output->writeMapBegin(TType::STRING, TType::STRUCT, count($this->taskInfos));
        {
          foreach ($this->taskInfos as $kiter23 => $viter24)
          {
            $xfer += $output->writeString($kiter23);
            $xfer += $viter24->write($output);
          }
        }
        $output->writeMapEnd();
      }
      $xfer += $output->writeFieldEnd();
    }
    if ($this->queryInfos !== null) {
      if (!is_array($this->queryInfos)) {
        throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
      }
      $xfer += $output->writeFieldBegin('queryInfos', TType::MAP, 2);
      {
        $output->writeMapBegin(TType::STRING, TType::STRING, count($this->queryInfos));
        {
          foreach ($this->queryInfos as $kiter25 => $viter26)
          {
            $xfer += $output->writeString($kiter25);
            $xfer += $output->writeString($viter26);
          }
        }
        $output->writeMapEnd();
      }
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class TCommandProcessorResponse {
  static $_TSPEC;

  public $message = null;
  public $responseCode = null;
  public $SQLState = null;
  public $ready = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'message',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'responseCode',
          'type' => TType::I32,
          ),
        3 => array(
          'var' => 'SQLState',
          'type' => TType::STRING,
          ),
        4 => array(
          'var' => 'ready',
          'type' => TType::BOOL,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['message'])) {
        $this->message = $vals['message'];
      }
      if (isset($vals['responseCode'])) {
        $this->responseCode = $vals['responseCode'];
      }
      if (isset($vals['SQLState'])) {
        $this->SQLState = $vals['SQLState'];
      }
      if (isset($vals['ready'])) {
        $this->ready = $vals['ready'];
      }
    }
  }

  public function getName() {
    return 'TCommandProcessorResponse';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->message);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->responseCode);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->SQLState);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 4:
          if ($ftype == TType::BOOL) {
            $xfer += $input->readBool($this->ready);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('TCommandProcessorResponse');
    if ($this->message !== null) {
      $xfer += $output->writeFieldBegin('message', TType::STRING, 1);
      $xfer += $output->writeString($this->message);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->responseCode !== null) {
      $xfer += $output->writeFieldBegin('responseCode', TType::I32, 2);
      $xfer += $output->writeI32($this->responseCode);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->SQLState !== null) {
      $xfer += $output->writeFieldBegin('SQLState', TType::STRING, 3);
      $xfer += $output->writeString($this->SQLState);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->ready !== null) {
      $xfer += $output->writeFieldBegin('ready', TType::BOOL, 4);
      $xfer += $output->writeBool($this->ready);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

class HiveServerException extends TException {
  static $_TSPEC;

  public $message = null;
  public $errorCode = null;
  public $SQLState = null;

  public function __construct($vals=null) {
    if (!isset(self::$_TSPEC)) {
      self::$_TSPEC = array(
        1 => array(
          'var' => 'message',
          'type' => TType::STRING,
          ),
        2 => array(
          'var' => 'errorCode',
          'type' => TType::I32,
          ),
        3 => array(
          'var' => 'SQLState',
          'type' => TType::STRING,
          ),
        );
    }
    if (is_array($vals)) {
      if (isset($vals['message'])) {
        $this->message = $vals['message'];
      }
      if (isset($vals['errorCode'])) {
        $this->errorCode = $vals['errorCode'];
      }
      if (isset($vals['SQLState'])) {
        $this->SQLState = $vals['SQLState'];
      }
    }
  }

  public function getName() {
    return 'HiveServerException';
  }

  public function read($input)
  {
    $xfer = 0;
    $fname = null;
    $ftype = 0;
    $fid = 0;
    $xfer += $input->readStructBegin($fname);
    while (true)
    {
      $xfer += $input->readFieldBegin($fname, $ftype, $fid);
      if ($ftype == TType::STOP) {
        break;
      }
      switch ($fid)
      {
        case 1:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->message);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 2:
          if ($ftype == TType::I32) {
            $xfer += $input->readI32($this->errorCode);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        case 3:
          if ($ftype == TType::STRING) {
            $xfer += $input->readString($this->SQLState);
          } else {
            $xfer += $input->skip($ftype);
          }
          break;
        default:
          $xfer += $input->skip($ftype);
          break;
      }
      $xfer += $input->readFieldEnd();
    }
    $xfer += $input->readStructEnd();
    return $xfer;
  }

  public function write($output) {
    $xfer = 0;
    $xfer += $output->writeStructBegin('HiveServerException');
    if ($this->message !== null) {
      $xfer += $output->writeFieldBegin('message', TType::STRING, 1);
      $xfer += $output->writeString($this->message);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->errorCode !== null) {
      $xfer += $output->writeFieldBegin('errorCode', TType::I32, 2);
      $xfer += $output->writeI32($this->errorCode);
      $xfer += $output->writeFieldEnd();
    }
    if ($this->SQLState !== null) {
      $xfer += $output->writeFieldBegin('SQLState', TType::STRING, 3);
      $xfer += $output->writeString($this->SQLState);
      $xfer += $output->writeFieldEnd();
    }
    $xfer += $output->writeFieldStop();
    $xfer += $output->writeStructEnd();
    return $xfer;
  }

}

?>