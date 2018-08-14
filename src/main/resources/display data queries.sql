#display data queries

#counterparty queries 

#total buys, tota sells, net position and realized profit for each instrument for each counterparty

SELECT c.counterparty_name, i.instrument_name,
       SUM(case when d.deal_type='B' then 1 else 0 end) as total_buys,
       SUM(case when d.deal_type = 'S' then 1 else 0 end) as total_sells,
       SUM(case when d.deal_type='B' then 1 else 0 end) - 
       SUM(case when d.deal_type = 'S' then 1 else 0 end) as net_position,
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
       
       (SUM(case when d.deal_type='S' then d.deal_amount else 0 end)-
       SUM(case when d.deal_type='B' then d.deal_amount else 0 end)) * 
       SUM(case when d.deal_type = 'S' then deal_quantity else 0 end)
       +
       ((SUM(case when d.deal_type='B' then 1 else 0 end) - 
       SUM(case when d.deal_type = 'S' then 1 else 0 end)) *
       (case when (select d.deal_time from deal d where d.deal_time=MAX(d.deal_time)) then d.deal_amount else 0 end))
       #AVG(case when d.deal_type='B' then d.deal_amount else 0 end)) 
       as effective_profit
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
SELECT i.instrument_ID,
 SUM(case when d.deal_type='B' then 1 else 0 end) as total_buys,
 SUM(case when d.deal_type = 'S' then 1 else 0 end) as total_sells,
 SUM(case when d.deal_type='B' then 1 else 0 end) as total_buys,
 AVG(case when d.deal_type = 'B' then d.deal_quantity else 0 end) as average_buys,
 AVG(case when d.deal_type = 'S' then d.deal_quantity else 0 end) as average_sells
FROM deal d
JOIN instrument i ON d.deal_instrument_id=i.instrument_id
GROUP BY i.instrument_id;