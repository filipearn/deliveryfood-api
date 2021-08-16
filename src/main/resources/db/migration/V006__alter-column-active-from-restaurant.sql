UPDATE restaurant SET active = true WHERE true;

ALTER TABLE restaurant MODIFY COLUMN active TINYINT(1) NOT NULL;