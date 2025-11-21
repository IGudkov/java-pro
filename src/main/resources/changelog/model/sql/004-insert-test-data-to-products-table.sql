insert into products (account_number, balance, product_type, user_id)
values ('account_number1', 111.11, 'ACCOUT', (select id from users where username = 'MigrUser1'));

insert into products (account_number, balance, product_type, user_id)
values ('card_number1', 1111.11, 'CARD', (select id from users where username = 'MigrUser1'));

insert into products (account_number, balance, product_type, user_id)
values ('account_number2', 222.22, 'ACCOUT', (select id from users where username = 'MigrUser2'));

insert into products (account_number, balance, product_type, user_id)
values ('card_number2', 2222.22, 'CARD', (select id from users where username = 'MigrUser2'));

insert into products (account_number, balance, product_type, user_id)
values ('account_number3', 333.33, 'ACCOUT', (select id from users where username = 'MigrUser3'));

insert into products (account_number, balance, product_type, user_id)
values ('card_number3', 3333.33, 'CARD', (select id from users where username = 'MigrUser3'));
