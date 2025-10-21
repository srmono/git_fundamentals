

temp tables 

approval 
saledept....

jining query select * from usersview

CREATE VIEW master (
  
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    dob DATE,
     score INT CHECK (score BETWEEN 300 AND 900),
    score_date DATETIME DEFAULT CURRENT_TIMESTAMP,
);



-- 1️⃣ USERS TABLE
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    dob DATE,
    occupation VARCHAR(50),
    monthly_income DECIMAL(10,2),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2️⃣ CIBIL SCORES TABLE
CREATE TABLE cibil_scores (
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT CHECK (score BETWEEN 300 AND 900),
    score_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    source VARCHAR(50) DEFAULT 'Dummy Generator',
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- 3️⃣ CREDIT CARD APPLICATIONS TABLE
CREATE TABLE applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    card_type VARCHAR(50),
    application_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    approved_limit DECIMAL(10,2) DEFAULT 0,
    remarks TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- 4️⃣ TRANSACTIONS TABLE (OPTIONAL)
CREATE TABLE transactions (
    txn_id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT NOT NULL,
    amount DECIMAL(10,2),
    txn_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    merchant VARCHAR(100),
    category VARCHAR(50),
    FOREIGN KEY (application_id) REFERENCES applications(application_id)
        ON DELETE CASCADE
);


