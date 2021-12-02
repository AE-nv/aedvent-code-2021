fetch('./input.txt')
  .then(response => response.text())
  .then(data => {
    move(data)
    
        
        
});

const margin_left = 10;
const margin_top = 10;

function move(data){
    let submarine = document.getElementById('submarine')
    submarine.style.marginLeft = margin_left + 'px'
    submarine.style.marginTop = margin_top + 'px'
    let horizontal_pos = 0
    let depth = 0
    let aim = 0
    // Do something with your data
    const lines = data.split('\n')
    for (let i=0; i < lines.length; i++){
        let command = lines[i].split(/[ ]+/)[0]
        let number = parseInt(lines[i].split(/[ ]+/)[1])
        switch(command){
            case "forward":
                horizontal_pos += number
                depth += (aim * number)
                move_submarine(submarine, horizontal_pos, depth, i)
                break;
            case "up":
                aim -= number
                break;
            case "down":
                aim += number
                break;
            default:
                console.log("wrong command => skip this")
        }    
    }
    console.log(aim, horizontal_pos, depth)
    console.log("solution: ", horizontal_pos * depth)
}

function move_submarine(submarine, horizontal_pos, depth, i){
    
    setTimeout(function(){
        submarine.style.marginLeft = parseInt(margin_left + horizontal_pos) + 'px'
        submarine.style.marginTop = parseInt(margin_top + depth) + 'px'
        submarine.title = horizontal_pos + "," + depth
        location_span = document.getElementById('location')
        location_span.innerHTML = "Current location:" + horizontal_pos + " , current depth: " + depth
    }, 100 * i)
    

}

  