# Snapshots

#### Snapshots are created and updated automatically:

* New snapshot for current date is created when there is no snapshot for this date yet.
* If there is a snapshot for current date then the snapshot with current date will be updated.

#### Changes in data for which snapshots are created:

* New data is inserted into Person / PersonAddress / PersonLegalId entities;
* Current data is updated in Person / PersonAddress / PersonLegalId entities;
* Current data is deleted in Person / PersonAddress / PersonLegalId entities;

#### Doing anything with snapshots manually is not recommended as it may cause unexpected behaviour.

# Endpoints:

## Person Endpoints:

### /getPerson - GET

#### Returns information about person(-s), as well as their addresses and legal documents in JSON format. 
#### Endpoint requires two parameters:

* asOfDate (TEXT, mandatory) - determines whether to retrieve a
snapshot (if it exists for provided date) of data from database on a given date, or, if asOfDate is equal
to today's date, current data will be retrieved from the database (must be in format YYYY-MM-DD);
* personId (INT, optional) - if present, only the data for a person with provided id will
be retrieved;
  
### /updatePerson - POST

#### Updates existing person within the database with new information. 
#### Endpoint requires one parameter:

* personId (INT, mandatory) - determines for which person data will be updated;

#### And one JSON body with:

* givenName (CHAR(50), mandatory);
* familyName (CHAR(50), mandatory);
* birthDate (TEXT, mandatory) - must be in format YYYY-MM-DD;
* gender (CHAR(1), mandatory);

### /insertPerson - POST

#### Insert new person into database.
#### Endpoint requires one JSON body with:

* givenName (CHAR(50), mandatory);
* familyName (CHAR(50), mandatory);
* birthDate (TEXT, mandatory) - must be in format YYYY-MM-DD;
* gender (CHAR(1), mandatory);

### /deletePerson - DELETE

#### Deletes existing person from database. Also deletes all persons addresses and legal ids.
#### Endpoint requires one parameter:

* personId (INT, mandatory) - determines which person will be deleted;

### /getSnapshotList - GET

#### Retrieves a list with snapshot dates (if they exist) in JSON format.

## PersonAddress Endpoints:

### /getPersonAddress - GET

#### Returns information about persons address(-es) in JSON format.
#### Endpoint requires three parameters:

* asOfDate (TEXT, mandatory) - determines whether to retrieve a
  snapshot (if it exists for provided date) of data from database on a given date, or, if asOfDate is equal
  to today's date, current data will be retrieved from the database (must be in format YYYY-MM-DD);
* personId (INT, optional) - if present, only the address(-es) for a person with provided id will
  be retrieved;
* addressType (CHAR(50), optional) - if present, only the address(-es) with provided addressType will
  be retrieved (must be either "declared" or "home");
  
### /updatePersonAddress - POST

#### Updates existing persons address within the database with new information.
#### Endpoint requires two parameters:

* personId (INT, mandatory) - determines whose address will be updated;
* addressType (CHAR(50), mandatory) - determines which address will be updated (must be
  either "declared" or "home");

#### And one JSON body with:

* city (CHAR(50), mandatory);
* street (CHAR(50), mandatory);
* appartment (CHAR(50), optional);

### /insertPersonAddress - POST

#### Insert new address into database.
#### Endpoint requires one JSON body with:

* personId (INT, mandatory);
* addressType (CHAR(50), mandatory) - must be either "declared" or "home";
* city (CHAR(50), mandatory);
* street (CHAR(50), mandatory);
* appartment (CHAR(50), optional);

### /deletePersonAddress - DELETE

#### Deletes existing address from database.
#### Endpoint requires two parameters:

* personId (INT, mandatory) - determines whose address will be deleted;
* addressType (CHAR(50), mandatory) - determines which address will be deleted (must be
either "declared" or "home");

## PersonLegalId Endpoints:

### /getPersonLegalId - GET

#### Returns information about persons legal id(-s) in JSON format.
#### Endpoint requires three parameters:

* asOfDate (TEXT, mandatory) - determines whether to retrieve a
  snapshot (if it exists for provided date) of data from database on a given date, or, if asOfDate is equal
  to today's date, current data will be retrieved from the database (must be in format YYYY-MM-DD);
* personId (INT, optional) - if present, only the legal id(-s) for a person with provided id will
  be retrieved;
* idType (CHAR(50), optional) - if present, only the legal id(-s) with provided idType will
  be retrieved (must be either an "id card" or a "passport");

### /updatePersonLegalId - POST

#### Updates existing persons legal id within the database with new information.
#### Endpoint requires two parameters:

* personId (INT, mandatory) - determines whose legal id will be updated;
* idType (CHAR(50), mandatory) - determines which legal id will be updated (must be
  either an "id card" or a "passport");

#### And one JSON body with:

* idNumber (INT, mandatory);
* issueDate (TEXT, mandatory) - must be in format YYYY-MM-DD;
* issuedBy (CHAR(50), optional);

### /insertPersonLegalId - POST

#### Insert new legal id into database.
#### Endpoint requires one JSON body with:

* personId (INT, mandatory);
* idType (CHAR(50), mandatory) - must be either an "id card" or a "passport";
* idNumber (INT, mandatory);
* issueDate (TEXT, mandatory) - must be in format YYYY-MM-DD;
* issuedBy (CHAR(50), optional);

### /deletePersonLegalId - DELETE

#### Deletes existing legal id from database.
#### Endpoint requires two parameters:

* personId (INT, mandatory) - determines whose legal id will be deleted;
* idType (CHAR(50), mandatory) - determines which legal id will be deleted (must be
  either an "id card" or a "passport");
