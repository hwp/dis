
drop table EstateAgent
create table EstateAgent ( \
  ID int not null primary key generated always as identity (start with 1 increment by 1), \
  Name varchar(20) not null, \
  Address varchar(40) not null, \
  Login varchar(20), \
  Password varchar(20) not null \
)

drop table Estate
create table Estate ( \
  ID int not null primary key generated always as identity (start with 1 increment by 1), \
  City varchar(40) not null, \
  PostalCode int not null, \
  Street varchar(40) not null, \
  StreetNumber int not null, \
  SquareArea int not null, \
  Manager int not null, \
  foreign key(Manager) references EstateAgent(ID) \
)

drop table Apartment
create table Apartment ( \
  ID int not null primary key, \
  Floor int not null, \
  Rent int not null, \
  Rooms int not null, \
  Balcony char(1) not null, \
  Kitchen char(1) not null, \
  foreign key(ID) references Estate(ID) \
)

drop table House
create table House ( \
  ID int not null primary key, \
  Floors int not null, \
  Price int not null, \
  Garden char(1) not null, \
  foreign key(ID) references Estate(ID) \
)

drop table Person
create table Person ( \
  ID int not null primary key generated always as identity (start with 1 increment by 1), \
  FirstName varchar(20) not null, \
  Name varchar(20) not null, \
  Address varchar(40) not null \
)

drop table Contract
create table Contract ( \
  ContractNo int not null primary key generated always as identity (start with 1 increment by 1), \
  CDate date not null, \
  Place varchar(40) not null \
)

drop table TenancyContract
create table TenancyContract ( \
  ContractNo int not null primary key, \
  StartDate date not null, \
  Duration varchar(20) not null, \
  AddtionalCosts int not null, \
  Tenant int not null, \
  AptID int not null, \
  foreign key(ContractNo) references Contract(ContractNo), \
  foreign key(Tenant) references Person(ID), \
  foreign key(AptID) references Apartment(ID) \
)

drop table PurchaseContract
create table PurchaseContract ( \
  ContractNo int not null primary key, \
  NoOfInstallments int not null, \
  InterestRate float not null, \
  Purchaser int not null, \
  HouseID int not null, \
  foreign key(ContractNo) references Contract(ContractNo), \
  foreign key(Purchaser) references Person(ID), \
  foreign key(HouseID) references House(ID) \
)

