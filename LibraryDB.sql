{\rtf1\ansi\ansicpg1252\cocoartf1265\cocoasubrtf190
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural

\f0\fs24 \cf0 drop table Borrower;\
drop table BorrowerType;\
drop table Book;\
drop table HasAuthor;\
drop table HasSubject;\
drop table BookCopy;\
drop table HoldRequest;\
drop table Borrowing;\
drop table Fine;\
\
create table Borrower (\
Bid integer not null PRIMARY KEY,\
Password char(20),\
Name char(20),\
Address char(20),\
Phone integer,\
EmailAddress char(20),\
SinOrStNo integer,\
ExpiryDate date,\
Type char(20), \
FOREIGN KEY (Type) REFERENCES BookType);\
\
create table BorrowerType (\
Type char(20) not null PRIMARY KEY,\
BookTimeLimit integer);\
\
create table Book (\
CallNumber char(20) not null PRIMARY KEY,\
ISBN integer,\
Title char(20),\
MainAuthor char(20),\
Publisher char(20),\
Year integer);\
\
create table HasAuthor (\
CallNumber char(20) not null,\
Name char(20) not null,\
PRIMARY KEY(CallNumber, Name),\
FOREIGN KEY (CallNumber) REFERENCES Book);\
\
create table HasSubject (\
CallNumber char(20) not null,\
Subject char(20) not null,\
PRIMARY KEY (CallNumber, Subject),\
FOREIGN KEY (CallNumber) REFERENCES Book);\
\
create table BookCopy (\
CallNumber char(20) not null,\
CopyNo integer not null,\
Status char(20),\
PRIMARY KEY (CallNumber, CopyNo),\
FOREIGN KEY (CallNumber) REFERENCES Book);\
\
create table HoldRequest (\
Hid integer not null PRIMARY KEY,\
Bid integer,\
CallNumber char(20),\
IssuedDate date,\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural
\cf0 FOREIGN KEY (Bid) REFERENCES Borrower,\
FOREIGN KEY (CallNumber) REFERENCES Book);\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural
\cf0 \
create table Borrowing (\
Borid integer not null PRIMARY KEY,\
Bid integer,\
CallNumber char(20),\
CopyNo integer,\
OutDate date,\
InDate date,\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural
\cf0 FOREIGN KEY (Bid) REFERENCES Borrower,\
FOREIGN KEY (CallNumber) REFERENCES Book);\
\
create table Fine (\
Fid integer not null PRIMARY KEY,\
Amount integer,\
IssuedDate date,\
PaidDate date,\
Borid integer,\
FOREIGN KEY (Borid) REFERENCES Borrowing);\
\
Alter table Borrower add (\
constraint bid_random PRIMARY KEY (Bid));\
\
Create SEQUENCE bor_seq;\
\
Create or Replace TRIGGER bor_trig\
Before insert on Borrower\
for each row\
\
begin\
select bor_seq.NEXTVAL\
into :new.Bid\
from dual;\
END;\
/\
\
insert into Borrower(Password, Name, Address, Phone, EmailAddress, SinOrStNo, ExpiryDate,  Type) values (\'91ewer\'92, \'91May Smith\'92, \'91123 Up St.\'92, 111-1111, \'91may.smith@gee.com\'92, 12345678, \'9201/01/2015\'92, \'91Student\'92);\
\
insert into Borrower(Password, Name, Address, Phone, EmailAddress, SinOrStNo, ExpiryDate,  Type) values (\'91asdf\'92, \'91John Doe\'92, \'91456 Down St.\'92, 222-2222, \'91john.doe@gee.com\'92, 21345678, \'9201/31/2015\'92, \'91Staff\'92);\
\
insert into Borrower(Password, Name, Address, Phone, EmailAddress, SinOrStNo, ExpiryDate,  Type) values (\'91xxcv\'92, \'91Charles Dew\'92, \'91789 Right St.\'92, 333-3333, \'91charles.dew@gee.com\'92, 87654321, \'9205/05/2015\'92, \'91Faculty\'92);\
\
insert into BorrowerType values\
(\'91Student\'92, 2);\
\
insert into BorrowerType values (\'91Faculty\'92, 12);\
\
insert into BorrowerType values (\'91Staff\'92, 6);\
\
insert into Book values\
(\'91reed1234copyno1\'92, 12-34-56, \'91Blinded Cat\'92, \'91Mike Reed\'92, \'91BookPublisher\'92, 2010);\
\
insert into Book values\
(\'91dwight345copyno2\'92, 34-21-65, \'91Cute Cuddle Bat\'92, \'91Leet Dwight\'92, \'91DreamBook\'92, 2011);\
\
insert into HasAuthor (\'91reed1234copyno1\'92, \'91Paul Tate\'92);\
\
insert into HasAuthor (\'91dwight345copyno2\'92, \'91Amy Pitt\'92);\
\
insert into HasSubject (\'91reed1234copyno1\'92, \'91Fantasy\'92);\
\
insert into HasSubject (\'91dwight345copyno2\'92, \'91Adventure\'92);\
\
}