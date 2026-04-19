/* Roles */
INSERT INTO authorities(authority) VALUES('ROLE_ADMIN');
INSERT INTO authorities(authority) VALUES('ROLE_SUPERVISOR');
INSERT INTO authorities(authority) VALUES('ROLE_CONCIERGE');

/* Categories */
INSERT INTO categories(name, description) VALUES('Towels', 'C Fold Towels');
INSERT INTO categories(name, description) VALUES('Soap', 'Hand Soap');
INSERT INTO categories(name, description) VALUES('Toilet', 'Toilet Paper');

/* Products */
INSERT INTO products (code, name) VALUES('G-P19880-01', '2 Ply Bathroom Tissue');
INSERT INTO products (code, name) VALUES('HOS6141', 'Hand Soap 1600mL');

/* Supply Items */
INSERT INTO supply_items(name, code, description, specification, create_at, category_id, dim_length, dim_width, dim_height, dim_weight, dim_uom, pkg_units_per_pack, pkg_packs_per_case, pkg_cases_per_pallet, pkg_uom) VALUES('Ultra Towel','ACM-123', 'Ultra towel scot old brand', 'No specifications', NOW(),1, 20.0, 20.0, 25.0, 1.2, 'CM', 100, 12, 30, 'CASE');
INSERT INTO supply_items(name, code, description, specification, create_at, category_id, dim_length, dim_width, dim_height, dim_weight, dim_uom, pkg_units_per_pack, pkg_packs_per_case, pkg_cases_per_pallet, pkg_uom) VALUES('Blue Sea','REVO321103', 'Toilet cheap paper', 'No ideal for executive', NOW(), 3, 23.0, 25.0, 30.0, 3.2, 'CM', 200, 20, 60, 'CASE');
INSERT INTO supply_items(name, code, description, specification, create_at, category_id, dim_length, dim_width, dim_height, dim_weight, dim_uom, pkg_units_per_pack, pkg_packs_per_case, pkg_cases_per_pallet, pkg_uom) VALUES('Scott','SCOTT20283', 'Soft toilet paper', 'Ideal for executive', NOW(), 3, 23.0, 25.0, 30.0, 3.2, 'CM', 200, 20, 60, 'CASE');

/* Customer Accounts */
INSERT INTO customer_accounts (external_code, name, email) VALUES('000001', 'First Class CC', '');

/* Customer Sites */
INSERT INTO customer_sites (external_code, address, customer_id) VALUES('00202001', '600 Hurtsbourne Ln pkwy', 1);
INSERT INTO customer_sites (external_code, address, customer_id) VALUES('0004444', '700 Hurstbourne pkwy', 1);

/* Users */
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('John', 'Doe', 'johndoe@gmail.com', '502-444-3155', 1, '$2a$10$6tZjP9xVRKxjOLev.pLryeKKgphVK93APy3W9LQNQ1f8LE16RZxte', 1, '2017-08-25', TRUE);
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('Pepito', 'Perez', 'pepito@gmail.com', '503-993-9333', 2, '$2a$10$6f9d.mEso.FidNCwGeTzJOnOsZeacdvmDVXwub70pgwJqpuysNvNq', 1, '2017-08-25', FALSE);
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('Juanita', 'Perez', 'juana@gmail.com', '889-888-6543', 3, '$2a$10$Jx1sq2OphqlseH3Nr/svfegccxQb7VN5RHGXXVO764mF1.h.7sO7u', 2, '2023-08-25', TRUE);
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('Michael', 'Love', 'michael@gmail.com', '503-887-9087', 2, '$2a$10$Jx1sq2OphqlseH3Nr/svfegccxQb7VN5RHGXXVO764mF1.h.7sO7u', 1, '2023-05-15', TRUE);

/* Requests */
INSERT INTO requests(description, additional_items, status, user_id, site_id, create_at) VALUES('Cleaning Items Supply', 'Toilet Desinfectant, Toilet Brushes', FALSE, 1, 1, NOW());
INSERT INTO requests(description, additional_items, status, user_id, site_id, create_at) VALUES('Toilet and Wax bags', 'Toilet Brusheds, Wax Bag', FALSE, 1, 1, NOW());
INSERT INTO requests(description, additional_items, status, user_id, site_id, create_at) VALUES('Cleaning', '', FALSE, 2, 1, NOW());

/* Request Items */
INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(2,1,1);
INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(4,1,1);
INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(5,2,2);
INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(1,2,2);

/* Invitation Codes */
INSERT INTO invitation_codes(code, is_used, created_by_user_id, used_by_user_id, create_at) VALUES('123', TRUE, 1, 3, NOW());