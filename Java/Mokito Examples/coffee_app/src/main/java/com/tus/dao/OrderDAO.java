package com.tus.dao;

import java.sql.SQLException;

import com.tus.orders.Customer;

public interface OrderDAO  {
	Customer findCustomerForId(long customerAccountId) throws SQLException;
	
}
