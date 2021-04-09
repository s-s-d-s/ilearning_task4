const faker = require('faker');
const csv = require('minimal-csv-formatter');

function generatefakeusers(record_numbers, locale){

    if (record_numbers <= 0) return "Error. Record numbers must be > 0!"

    let gender, firstName, lastName, middleName, city, streetName, zipcode, phone;
    faker.locale = locale;

    for (let i = 0; i < record_numbers; i++) {
        gender = Math.random()<0.5?0:1;
        firstName = faker.name.firstName(gender);
        lastName = faker.name.lastName(gender);
        middleName = faker.name.middleName(gender);
        city = faker.address.cityName();
        streetName = faker.address.streetName();
        zipcode = faker.address.zipCode();
        phone = faker.phone.phoneNumber();

        console.log(csv([firstName, lastName, middleName, city, streetName, zipcode, phone]));
    }

}
generatefakeusers(process.argv[2], process.argv[3]);

