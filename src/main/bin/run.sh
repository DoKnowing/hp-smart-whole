#!/bin/sh
print_usage ()
{
  echo "Usage: sh run.sh COMMAND"
  echo "where COMMAND is one of the follows:"
# 待添加
  exit 1
}

## help
if [ $# = 0 ] || [ $1 = "help" ]; then
  print_usage
fi

COMMAND=$1
shift

if [ "$JAVA_HOME" = "" ]; then
  echo "Error: JAVA_HOME is not set."
  exit 1
fi


JAVA=${JAVA_HOME}/bin/java
HEAP_OPTS="-Xmx1000m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/dump -XX:+UseConcMarkSweepGC"


JAR_NAME=`ls |grep jar-with-dependencies`

CLASSPATH=${CLASSPATH}:${JAVA_HOME}/lib/tools.jar
CLASSPATH=${CLASSPATH}:conf
CLASSPATH=${CLASSPATH}:${JAR_NAME}
#for f in lib/*.jar; do
#  CLASSPATH=${CLASSPATH}:${f};
#done

params=$@

## 待修改
if [ "$COMMAND" = "QueueManageCli" ]; then
    CLASS=com.ds.octopus.job.cli.QueueManageCli
elif [ "$COMMAND" = "RandomGroupCli" ]; then
    CLASS=com.ds.octopus.tools.randomgroup.RandomGroupCli
elif [ "$COMMAND" = "NewsforumInfoCli" ]; then
    CLASS=com.ds.octopus.tools.newsforumInfo.newsforumInfoCli
elif [ "$COMMAND" = "EsDataFetchCli" ]; then
    CLASS=com.ds.octopus.tools.es.EsDataFetchCli
elif [ "$COMMAND" = "TagCli" ]; then
    CLASS=com.ds.octopus.tools.tag.TagCli
elif [ "$COMMAND" = "WxArticleUpdateQuartzCli" ]; then
    CLASS=com.ds.octopus.job.updateArticles.WxArticleUpdateQuartzCli
else
    CLASS=${COMMAND}
fi

"$JAVA" -Djava.io.tmpdir=/var/spark/tmp -Djava.awt.headless=true ${HEAP_OPTS} -classpath "$CLASSPATH" ${CLASS} ${params}