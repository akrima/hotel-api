--
INSERT INTO hotelSchema.users (username, password, is_admin) VALUES
('user1', 'password1', false),
('user2', 'password2', true),
('user3', 'password3', false),
('user4', 'password4', false),
('user5', 'password5', true),
('user6', 'password6', false),
('user7', 'password7', true),
('user8', 'password8', false),
('user9', 'password9', false),
('user10', 'password10', true);

-- Ajout des liens des images pour les chambres
-- Ajout des liens des images pour les chambres avec descriptions
INSERT INTO hotelSchema.rooms (room_number, available, price, images, description) VALUES
('101', true, 100.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/4.jpeg'
], 'Cozy room with a view of the city. Ideal for a relaxing stay.'),

('102', false, 150.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/4.jpeg'
], 'Spacious suite with luxurious amenities. Currently unavailable.'),

('103', false, 120.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1-1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2-2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3-3.jpeg'
], 'Modern room with a minimalist design. Perfect for a comfortable stay.'),

('104', true, 90.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/4.jpeg'
], 'Budget-friendly room with essential amenities.'),

('105', false, 200.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1-1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2-2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3-3.jpeg'
], 'Luxurious suite with a private balcony and panoramic views.'),

('106', true, 110.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/4.jpeg'
], 'Comfortable room with modern amenities for a pleasant stay.'),

('107', false, 180.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1-1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2-2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3-3.jpeg'
], 'Spacious suite with a separate living area and elegant decor.'),

('108', true, 95.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/4.jpeg'
], 'Charming room with a blend of traditional and modern design.'),

('109', false, 160.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1-1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2-2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3-3.jpeg'
], 'Modern and stylish room with thoughtful design elements.'),

('110', true, 130.0, ARRAY[
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/1.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/2.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/3.jpeg',
    'https://akrimabuckets3.s3.ca-central-1.amazonaws.com/4.jpeg'
], 'Classic room with elegant decor. Enjoy a pleasant and relaxing atmosphere.');



--
INSERT INTO hotelSchema.reservations (start_date, end_date, room_id, user_id) VALUES
('2024-01-15', '2024-01-20', 1, 1),
('2024-02-01', '2024-02-05', 2, 2),
('2024-03-10', '2024-03-15', 3, 3),
('2024-04-05', '2024-04-10', 4, 4),
('2024-05-20', '2024-05-25', 5, 5),
('2024-06-15', '2024-06-20', 6, 6),
('2024-07-03', '2024-07-08', 7, 7),
('2024-08-18', '2024-08-23', 8, 8),
('2024-09-12', '2024-09-17', 9, 9),
('2024-10-25', '2024-10-30', 10, 10);
