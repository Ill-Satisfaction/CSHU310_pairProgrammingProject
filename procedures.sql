--createItem
DELIMITER $$
CREATE PROCEDURE createItem(IN itemCode VARCHAR(10), IN itemDesc VARCHAR(50), IN price DECIMAL(4,2))
BEGIN
INSERT INTO Item (ItemCode, ItemDescription, Price)
VALUES (itemCode, itemDesc, price);
END $$

--createPurchase
DELIMITER $$
CREATE PROCEDURE createPurchase(IN iCode VARCHAR(10), IN quan INT)
BEGIN
INSERT INTO Purchase (ItemID, Quantity)
VALUES ((SELECT DISTINCT Item.ID FROM Item WHERE Item.ItemCode = iCode), quan);
END $$

--createShipment
DELIMITER $$
CREATE PROCEDURE createShipment(IN itemCode VARCHAR(10), IN itemQuantity INT,IN shipmentDate DATE)
BEGIN
INSERT INTO Shipment (ItemID, Quantity, ShipmentDate)
VALUES ((SELECT DISTINCT Item.ID FROM Item WHERE Item.ItemCode = itemCode), itemQuantity, shipmentDate);
END $$

--deleteItem
DELIMITER $$

CREATE PROCEDURE checkDeleteItem (
IN itemCode VARCHAR(10)
)
BEGIN
select i.ID as "Conflicts: (NOTE: any conflicts will cancel delete)", 
s.ID as "shipment ID", 
p.ID as "purchase ID"
from Item i
left join Shipment s on i.ID = s.ItemID
left join Purchase p on i.ID = p.ItemID
where i.ItemCode = itemCode AND (s.ID IS NOT NULL or p.ID IS NOT NULL);
END $$

DELIMITER $$
CREATE PROCEDURE deleteItem (
IN itemCode VARCHAR(10)
)
BEGIN
DELETE i
FROM Item i 
LEFT JOIN Shipment s on i.ID = s.ItemID 
LEFT JOIN Purchase p on i.ID = p.ItemID
WHERE ItemCode = itemCode AND s.ID IS NULL AND p.ID IS NULL;
END $$

-- itemsAvailableOne
DELIMITER $$
CREATE PROCEDURE itemsAvailableOne(IN itemCode VARCHAR(10))
BEGIN
SELECT i.ItemCode, i.ItemDescription,
SUM(IFNULL(s.Quantity, 0) - IFNULL(p.Quantity, 0)) AS ItemsAvailable
FROM Item i
LEFT JOIN Shipment s
	ON i.ID = s.ItemID
LEFT JOIN Purchase p
	ON i.ID = p.ItemID
WHERE ItemCode = itemCode
GROUP BY i.ItemCode;
END $$

-- itemsAvailableAll
DELIMITER $$
CREATE PROCEDURE itemsAvailableAll()
BEGIN
SELECT i.ItemCode, i.ItemDescription, 
SUM(IFNULL(s.Quantity, 0) - IFNULL(p.Quantity,0)) AS ItemsAvailable
FROM Item i
LEFT JOIN Shipment s
	ON i.ID = s.ItemID
LEFT JOIN Purchase p
	ON i.ID = p.ItemID
GROUP BY i.ItemCode;
END $$