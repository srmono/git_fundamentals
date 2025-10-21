import React, { useState } from 'react'
import customers from './customers.json';
import CustomerDetails from './CustomerDetails';
import { Link } from 'react-router';

function CustomersList() {

    const [customer, setCustomer] = useState({});

    //local data for customers
    //...cusotmers, update

    const onSelectHandeler = (e, cust) => {
        console.log(cust);
        setCustomer(cust)
    }
    
    const cinfo = customers.map( (cust, i) => {
        return (
            <li onClick={ (e) => onSelectHandeler(e, cust) } key={i}>
                
                <Link to={`/customers/${cust.id}`}>
                  {cust.firstName}
                </Link>

            </li>
        )
    } )
  return (
    <div>
      <ul>
        {cinfo}

        {/* <CustomerDetails  customer={customer}/> */}
        
      </ul>
    </div>
  )
}

export default CustomersList
