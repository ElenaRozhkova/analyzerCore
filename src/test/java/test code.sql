#TESTS

#make sure all tables are populating correctly>> for user requirement 3
SELECT * FROM counterparty;
SELECT * FROM instrument;
SELECT * FROM deal;
SELECT * FROM users;

#adding filters to see specific patterns of the data tables >> for user requirement 3
SELECT * FROM counterparty WHERE counterparty_id=1;
SELECT * FROM instrument WHERE instrument_name='Astronomica';
SELECT * FROM deal WHERE deal_amount > 3000;
SELECT * FROM users WHERE user_id='alison';

#make sure all accurate fields are in each table >> for user requirement 4
SHOW COLUMNS FROM counterparty;
SHOW COLUMNS FROM instrument;
SHOW COLUMNS FROM deal;
SHOW COLUMNS FROM users;

#create links between tables to test the correlations between data entities

#find the deal_id and names of all the counterparties whose deals are made using astronomica as the instrument
SELECT d.deal_id, c.counterparty_name FROM deal d 
LEFT JOIN counterparty c ON c.counterparty_id=d.deal_counterparty_id
WHERE d.deal_instrument_id=1;
