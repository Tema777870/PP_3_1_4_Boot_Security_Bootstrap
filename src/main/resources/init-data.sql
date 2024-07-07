insert into table231.users(age, email, name, password, last_name) VALUES (25,'admin@admin','admin','$2a$12$DzQpIs0O5uSWvZis.YO7UeT2iYlVJtLzUHz6FBH3ZE7LlU9TuAShG','adminov');
insert into table231.roles(id, name) VALUES (1, 'ROLE_USER');
insert into table231.roles(id, name) VALUES (2, 'ROLE_ADMIN');
insert into table231.users_roles(user_id, roles_id) VALUES (1,1);
insert into table231.users_roles(user_id, roles_id) VALUES (1,2);