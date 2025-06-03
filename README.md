# SeF-assessment-4-group-104-
addPerson assumption:
- All fields are mandatory
- birthDate and address will be formatted by and selected through an API
- birthDate will be selected through date picker 
- address can only be selected after the user has picked from an auto filling address search engine
- verify name assumes that the person only has one first and last name that has no special characters
- when the user causes an error it does not immediatly end the session but instead just makes them redo it again

updatePersonalDetails Assumptions:
- New details follow the same assumptions and restrictions as addPerson
- If unchanged values are passed to the function by the API as null values
- Empty strings make no changes to existing data

addDemeritPoints Assumptions:
- If there are no errors but no valid demerits, return success.
- File only wants valid demerits.