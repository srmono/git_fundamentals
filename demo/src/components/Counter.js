import React, {useEffect, useState} from 'react'

function Counter() {
    //const counter = 0;
    
    const [counter, setCounter] = useState(0);

    const handleIncrement = () => {
        setCounter(counter + 1 )
        
    }

    useEffect( () => {
      document.title = `You have clicked ${counter} times`
      console.log("counter updating");
    }, [counter])

  return (
    <div>
       {/* create input, attach on change event, update user */}

       Counter value is: {counter}
       <button onClick={handleIncrement}>Increment</button>
    </div>
  )
}

export default Counter
