/* Products */
INSERT INTO products (code, name) VALUES('G-P19880-01', '2 Ply Bathroom Tissue');
INSERT INTO products (code, name) VALUES('HOS6141', 'Hand Soap 1600mL');
/* Roles */
INSERT INTO authorities(authority) VALUES("ROLE_ADMIN");
INSERT INTO authorities(authority) VALUES("ROLE_SUPERVISOR");
INSERT INTO authorities(authority) VALUES("ROLE_CONCIERGE");
/* Categories */
INSERT INTO categories(name, description) VALUES('Towels', 'C Fold Towels');
INSERT INTO categories(name, description) VALUES('Soap', 'Hand Soap');
INSERT INTO categories(name, description) VALUES('Toilet', 'Toilet Paper');
/* Supply Items */
INSERT INTO supply_items(name, code, description, specification, create_at, category_id,dim_length, dim_width, dim_height, dim_weight, dim_uom, pkg_units_per_pack, pkg_packs_per_case, pkg_cases_per_pallet, pkg_uom) VALUES('Ultra Towel','ACM-123', 'Ultra towel scot old brand', 'No specifications', NOW(),1, 20.0, 20.0, 25.0, 1.2, 'CM', 100, 12, 30, 'CASE');

INSERT INTO supply_items (name, code, description, specification, create_at, category_id, dim_length, dim_width, dim_height, dim_weight, dim_uom, pkg_units_per_pack, pkg_packs_per_case, pkg_cases_per_pallet, pkg_uom) VALUES('Blue Sea','REVO321103', 'Toilet cheap paper', 'No ideal for executive', NOW(), 3, 23.0, 25.0, 30.0, 3.2, 'CM', 200, 20, 60, 'CASE');

/* Customer Accounts*/
INSERT INTO customer_accounts (external_code, name, email) VALUES('000001', 'First Class CC', 'felipephez@gmail.com');
/* Customer Site */
INSERT INTO customer_sites (external_code, address, customer_id) VALUES('00202001', '600 Hurtsbourne Ln pkwy', 1);
INSERT INTO customer_sites (external_code, address, customer_id) VALUES('0004444', '700 Hurstbourne pkwy', 1);


/* Employees */
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('Andres', 'Penaranda', 'felipe@gmail.com', '502-644-9175',1, '$2a$10$6tZjP9xVRKxjOLev.pLryeKKgphVK93APy3W9LQNQ1f8LE16RZxte', 1, '2017-08-25', TRUE);
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('Ivonne', 'Borrero', 'ivonne@gmail.com', '502-644-3355', 2, '$2a$10$6f9d.mEso.FidNCwGeTzJOnOsZeacdvmDVXwub70pgwJqpuysNvNq', 1, '2017-08-25', FALSE);
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('laura', 'victoria', 'laura@gmail.com', '503-344-5465', 3, '$2a$10$Jx1sq2OphqlseH3Nr/svfegccxQb7VN5RHGXXVO764mF1.h.7sO7u', 2, '205-08-25', TRUE);
INSERT INTO users (first_name, last_name, email, phone, role_id, password, site_id, create_at, is_active) VALUES('sienna', 'borrero', 'sienna@gmail.com', '501-456-4545', 2, '$2a$10$Jx1sq2OphqlseH3Nr/svfegccxQb7VN5RHGXXVO764mF1.h.7sO7u', 1, '203-05-15', TRUE);


INSERT INTO requests(description, additional_items, status, user_id, site_id, create_at) VALUES('Cleaning Items Supply', 'Toilet Desinfectant, Toilet Brushes', FALSE, 1, 1, NOW());
INSERT INTO requests(description, additional_items, status, user_id, site_id, create_at) VALUES('Toilet and Wax bags', 'Toilet Brusheds, Wax Bag', FALSE, 1, 1, NOW());
INSERT INTO requests(description, additional_items, status, user_id, site_id, create_at) VALUES('Cleaning', '', FALSE, 2, 1, NOW());

INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(2,1,1);
INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(4,1,1);
INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(5,2,2);
INSERT INTO request_items(quantity, request_id, supply_item_id) VALUES(1,2,2);

INSERT INTO invitation_codes(code, is_used, created_by_user_id, used_by_user_id, create_at) VALUES('123', TRUE, 1, 3, NOW());
