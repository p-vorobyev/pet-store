insert into products(id, version, name, category, description, price) values
(nextval('hibernate_sequence'), 1, 'Kayak', 'Watersports', 'A boat for one person', 275),
(nextval('hibernate_sequence'), 1, 'Lifejacket', 'Watersports', 'Protective and fashionable', 48.95),
(nextval('hibernate_sequence'), 1, 'Soccer Ball', 'Soccer', 'FIFA-approved size and weight', 19.50),
(nextval('hibernate_sequence'), 1, 'Corner Flags', 'Soccer', 'Give your playing field a professional touch', 34.95),
(nextval('hibernate_sequence'), 1, 'Stadium', 'Soccer', 'Flat-packed 35,000-seat stadium', 79500),
(nextval('hibernate_sequence'), 1, 'Thinking Cap', 'Chess', 'Improve brain efficiency by 75%', 1),
(nextval('hibernate_sequence'), 1, 'Unsteady Chair', 'Chess', 'Secretly give your opponent a disadvantage', 29.95),
(nextval('hibernate_sequence'), 1, 'Human Chess Board', 'Chess', 'A fun game for the family', 75),
(nextval('hibernate_sequence'), 1, 'Bling Bling King', 'Chess', 'Gold-plated, diamond-studded King', 1200);

insert into cart(id, version, item_count, cart_price) values
(nextval('hibernate_sequence'), 0, 1, 3);

insert into cart_line(id, version, quantity, product_id, cart_id) values
(nextval('hibernate_sequence'), 0, 3, 6, 10);

insert into orders(id, version, name, address, city, state, zip, country, shipped, cart_id) values
(nextval('hibernate_sequence'), 0, 'TestOrder', 'Magic st.', 'Kazan', 'Tatarstan', '12345', 'Russia', true, 10);
