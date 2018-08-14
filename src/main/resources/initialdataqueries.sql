#display data queries

#counterparty queries 

#total buys, tota sells, net position and realized profit for each instrument for each counterparty

SELECT c.counterparty_name, i.instrument_name,
       SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) as total_buys,
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as total_sells,
       SUM(case when d.deal_type='B' then deal_quantity else 0 end) - 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end) as net_position,
       (SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-
       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end) as realized_profit
FROM deal d
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
GROUP BY c.counterparty_name,i.instrument_id;

#trying to implement effective rate
/*
SELECT c.counterparty_name, i.instrument_name,
       SUM(case when d.deal_type='B' then 1 else 0 end) as total_buys,
       SUM(case when d.deal_type = 'S' then 1 else 0 end) as total_sells,
       SUM(case when d.deal_type='B' then 1 else 0 end) - 
       SUM(case when d.deal_type = 'S' then 1 else 0 end) as net_position,
       (SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-
       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end) as realized_profit,
       (AVG(case when d.deal_type='S' then d.deal_amount else 0 end)-
       AVG(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end)
       +
       (((SUM(case when d.deal_type='B' then d.deal_quantity else 0 end) - 
       SUM(case when d.deal_type = 'S' then d.deal_quantity else 0 end)) *
       #(case when (select * from deal where deal_time=MAX(deal_time)) then d.deal_amount else 0 end)
       -
       AVG(case when d.deal_type='S' then d.deal_amount else 0 end))) as effective_profit
FROM deal d
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
GROUP BY c.counterparty_name,i.instrument_id;
*/

#counterparty ID, register date, status, net realized profit for each counterparty
#trying to implement effective profit/loss
SELECT c.counterparty_ID, c.counterparty_name, c.counterparty_date_registered, c.counterparty_status,
	(SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-
       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end) as net_realized_profit
FROM deal d
JOIN counterparty c ON d.deal_counterparty_id=c.counterparty_id
GROUP BY c.counterparty_id;


#instrument queries
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