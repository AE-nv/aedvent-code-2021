use std::fs;


fn calculate_position_first_star(data : Vec<&str> ){
    let mut xpos = 0;
    let mut ypos = 0;
    for value in data{
        if (&value[0..1]).eq("f") {
            xpos += &value[value.len()-1..value.len()].parse::<i32>().expect("parse error");
        }else if (&value[0..1]).eq("d"){
            ypos += &value[value.len()-1..value.len()].parse::<i32>().expect("parse error");
        }else if (&value[0..1]).eq("u") {
            ypos -= &value[value.len()-1..value.len()].parse::<i32>().expect("parse error");
        }
    }
    println!("solution: {}\n",xpos*ypos);
}

fn calculate_position_second_star(data : Vec<&str> ){
    let mut distance = 0;
    let mut aim = 0;
    let mut depth = 0;
    for value in data{
        if (&value[0..1]).eq("f") {
            let increase : &i32 = &value[value.len()-1..value.len()].parse::<i32>().expect("parse error");
            distance += increase;
            depth += aim *increase;
        }else if (&value[0..1]).eq("d"){
            aim += &value[value.len()-1..value.len()].parse::<i32>().expect("parse error");
        }else if (&value[0..1]).eq("u") {
            aim -= &value[value.len()-1..value.len()].parse::<i32>().expect("parse error");
        }
    }
    println!("solution: {}\n",distance*depth);
}

fn main() {
    let input = fs::read_to_string("./data/input.txt")
        .expect("Something went wrong reading the file");

    let data : Vec<&str> = input.split_terminator("\n").collect();

    calculate_position_second_star(data);
}


