import React, {useEffect, useState} from 'react'

function LifeCycle() {

    const [count, setCount] = useState(0);

    //mounting, update, unmount
    // useEffect( () => {
    //     //console.log("component mounted" );
    //     console.log("component changed: ", count);
    // }, [count])

    useEffect( () => {
       // const id = setInterval( () => console.log('click'), 1000)
        console.log("comp mounted");

        // return () => {
        //     clearInterval(id);
        //     console.log("cleaned on unmount");
        // }
    }, [])

    const handleIncrement = () => {
        setCount(count + 1 )
    }

  return (
    <div>
        <div> Timer is running in console</div>
      {/* <h1>LifeCycle concepts {count}</h1>
      <button onClick= {handleIncrement}> Increment </button> */}
    </div>
  )
}

export default LifeCycle
