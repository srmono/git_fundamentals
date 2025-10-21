import React from 'react'

function ArrayList() {
    const numbers = [1,2,3,4,5,6,7,8,9]
    let listItems = numbers.map( (num, index) => <li key={index}>{num}</li>)

  return (
    <div>
      <ul>
        {listItems}
      </ul>
    </div>
  )
}

export default ArrayList
