--createItem
DELIMITER $$
CREATE PROCEDURE createItem( 
IN itemCode VARCHAR(10), 
IN itemDesc VARCHAR(50),
IN price DECIMAL(4,2)
)
BEGIN
INSERT INTO Item (ItemCode, ItemDescription, Price)
VALUES (itemCode, itemDesc, price);
END $$

--createPurchase
DELIMITER $$
CREATE PROCEDURE createPurchase( 
IN id INT, 
IN quan INT
)
BEGIN
INSERT INTO Purchase (ItemID, Quantity)
VALUES (id, quan);
END $$

--createShipment
DELIMITER $$
CREATE PROCEDURE createShipment( 
IN itemID INT, 
IN itemQuantity INT,
IN shipmentDate DATE
)
BEGIN
INSERT INTO Shipment (ItemID, Quantity, ShipmentDate)
VALUES (itemCode, itemQuantity, shipmentDate);
END $$