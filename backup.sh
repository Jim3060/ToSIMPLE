#!/bin/bash
echo "Begin BackUp..."
DATABASE=Questionnaire
DATE=`date "+%Y%m%d%H%M%S"`-BackupFile
BACKUP_DIR=/home/tbxsx/mongodbBackUp/
if [ ! -d $BACKUP_DIR ];
	then
	mkdir -p "$BACKUP_DIR"

fi
mongodump -d $DATABASE -o $BACKUP_DIR+$DATE

MYSQL_DB=questionnaireServey
MYSQL_USER=root
MYSQL_PASSWORD=lulls520
MYSQL_DIR=/home/tbxsx/mysqlBackUp/
MYSQL_HOST=127.0.0.1
MYSQL_PORT=3306

if [ ! -d $MYSQL_DIR ];
        then
        mkdir -p "$MYSQL_DIR"

fi
OPTIONS=" -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USER --password=$MYSQL_PASSWORD $MYSQL_DB"
echo "OPTIONS:"
echo $OPTIONS
mysqldump $OPTIONS --result-file=$MYSQL_DIR+$DATE

echo "BackUp End"
