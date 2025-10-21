import React from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
// import customers from './customers.json';


function CustomerDetails() {

  const customers = [
    {id: 1, firstName: "Venkat"}
  ]
  
  const {cid} = useParams()
  const navigate = useNavigate()

  const customer = customers.find( (c) => c.id == cid )

  return (
    <div>
      <Link to="/"> Go Back</Link>
      
      <button 
      onClick={
        () => navigate(-1)
      }>
        navigate back
      </button>
      <h1> Customer details component {cid} </h1>
      <div>
        {/* {customer.firstName} */}
      </div>
    </div>
  )
}

// function CustomerDetails({customer}) {
//   return (
//     <div>
//       {customer.id}
//     </div>
//   )
// }

export default CustomerDetails
