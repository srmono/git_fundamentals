import React from 'react'

function ChildComp({name, sendToParent}) {
    
  return (
    <div>
      <h2> Child Component this name is from parent : {name}</h2>

      <button onClick={ () => sendToParent("This message from child") }> 
        send message 
    </button>
    
    </div>
  )
}

export default ChildComp
