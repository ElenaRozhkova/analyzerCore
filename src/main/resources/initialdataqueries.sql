#DISPLAY DATA QUERIES
#DISPLAY DATA QUERIES

#counterparty queries 
#total buys, total sells, net position, realized profit and effective rate for each instrument for each counterparty
#FULL TABLE W ALL COUNTERPARTIES
SELECT c.counterparty_name, i.instrument_name,
       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) as total_buys,
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as total_sells,
       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - 
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as net_position,
       (SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-
       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end) as realized_profit,
       (AVG(case when d.deal_type='S' then d.deal_amount else 0 end)-
       AVG(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end)
       +
       ((SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - 
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end))
	*
	   (SELECT deal_amount FROM deal ORDER BY deal_time desc LIMIT 1)
       -
       AVG(case when d.deal_type='S' then d.deal_amount else 0 end)) as effective_profit
FROM deal d
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
GROUP BY c.counterparty_name,i.instrument_id;

#for each counterparty
SELECT  i.instrument_name,
       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) as total_buys,
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as total_sells,
       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - 
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as net_position,
       (SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-
       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end) as realized_profit,
       (AVG(case when d.deal_type='S' then d.deal_amount else 0 end)-
       AVG(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end)
       +
       ((SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - 
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end))
	*
	   (SELECT deal_amount FROM deal ORDER BY deal_time desc LIMIT 1)
       -
       AVG(case when d.deal_type='S' then d.deal_amount else 0 end)) as effective_profit
FROM deal d
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
WHERE c.counterparty_id=1
GROUP BY i.instrument_id;

#for side table counterparty ID, register date, status, net position, net realized profit, net effective rate for each counterparty
SELECT c.counterparty_ID, c.counterparty_name, c.counterparty_date_registered, c.counterparty_status,
	SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - 
	SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as net_position,
	(SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-
	SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) as net_realized_profit,
           (AVG(case when d.deal_type='S' then d.deal_amount else 0 end)-
       AVG(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end)
       +
       ((SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - 
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end))
	*
	   (SELECT deal_amount FROM deal ORDER BY deal_time desc LIMIT 1)
       -
       AVG(case when d.deal_type='S' then d.deal_amount else 0 end)) as net_effective_profit
FROM deal d
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
WHERE c.counterparty_id=1;

#instrument buys for counterparty 1
SELECT i.instrument_name,
SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) as buys
FROM deal d
JOIN instrument i ON d.deal_instrument_id= i.instrument_id
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
WHERE counterparty_id=1
GROUP BY i.instrument_id;

#instrument sells for counterparty 1
SELECT i.instrument_name,
SUM(case when d.deal_type='S' then d.deal_quantity else 0 end) as sells
FROM deal d
JOIN instrument i ON d.deal_instrument_id= i.instrument_id
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
WHERE counterparty_id=1
GROUP BY i.instrument_id;

/*//////////////////////////////////////////////////////////////////////////////////////////////////*/
#instrument queries
/*
#need to add the trading price which is the most recent price of the instrument
SELECT i.instrument_ID,
 SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) as total_buys,
 SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as total_sells,
 AVG(case when d.deal_type = 'B' then d.deal_quantity else 0 end) as average_buys,
 AVG(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as average_sells
 #(select dd.deal_amount from deal dd where dd.deal_time=MAX(dd.deal_time))
FROM deal d
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
GROUP BY i.instrument_id;
*/


/*//////////////////////////////////////////////////////////////////////////////////////////////////*/
#time vs price grouped by instrument graph
SELECT i.instrument_name, d.deal_time, d.deal_amount 
FROM deal d
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
ORDER BY d.deal_time,i.instrument_name;

#avg instrument price 
SELECT i.instrument_name, AVG(d.deal_amount)
FROM deal d
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
WHERE i.instrument_id=1;


/*//////////////////////////////////////////////////////////////////////////////////////////////////*/
#front page
#average price bought
SELECT i.instrument_id instrument_id,
AVG(case when d.deal_type = 'B' then d.deal_amount else 0 end) as average_price_bought
FROM instrument i
JOIN deal d ON d.deal_instrument_id=i.instrument_id
WHERE i.instrument_id=1;

#volume bought
SELECT i.instrument_id instrument_id,
SUM(case when d.deal_type = 'B' then d.deal_quantity else 0 end) volume_bought
FROM instrument i
JOIN deal d ON d.deal_instrument_id=i.instrument_id
WHERE i.instrument_id=1;

#average price sold
SELECT i.instrument_id instrument_id,
AVG(case when d.deal_type = 'S' then d.deal_amount else 0 end) as average_price_sold
FROM instrument i
JOIN deal d ON d.deal_instrument_id=i.instrument_id
WHERE i.instrument_id=1;

#volume sold
SELECT i.instrument_id instrument_id,
SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) volume_sold
FROM instrument i
JOIN deal d ON d.deal_instrument_id=i.instrument_id
WHERE i.instrument_id=1;


#most traded by 
SELECT i.instrument_id instrument_id, c.counterparty_name,SUM(d.deal_quantity) 
FROM deal d
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
WHERE i.instrument_id=1
GROUP BY c.counterparty_name
ORDER BY SUM(d.deal_quantity) DESC
LIMIT 1;

#trading price
SELECT i.instrument_id, d.deal_amount
FROM deal d 
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
WHERE i.instrument_id=1
ORDER BY d.deal_time desc LIMIT 1;

/*//////////////////////////////////////////////////////////////////////////////////////////////////*/
#my portfolio page
#majority will be left blank bc we don't have data

#table of trades
SELECT d.deal_id, d.deal_time, d.deal_type, d.deal_amount,
d.deal_quantity, i.instrument_name, c.counterparty_name
FROM deal d
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
LIMIT 20;
