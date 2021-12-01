use std::fs;

fn read_data(path: &str) -> Vec<i32>{
    let contents = fs::read_to_string(path)
        .expect("Something went wrong reading the file");

    let numbers: Vec<i32> = contents
        .split_whitespace()
        .map(|s| s.parse().expect("parse error"))
        .collect();

    return numbers;
}

fn clean_data_using_sliding_window(data: &Vec<i32>) -> Vec<i32>{
    let mut output = Vec::new();

    for n in 0..(data.len()-2){
        output.push(data[n]+data[n+1]+data[n+2]);
    }
    return output;
}

fn calculate_increases(data: &Vec<i32>) -> i32{
    let mut prev_num = data[0];
    let mut depth_increases = 0;
    for x in data{
       if prev_num < *x {
           depth_increases += 1;
       }
        prev_num = *x;
    }
    return depth_increases;
}

fn main() {

    let data = read_data("./data/input.txt");

    let data = clean_data_using_sliding_window(&data);

    let solution = calculate_increases(&data);

    println!("solution: {}\n",solution);
}


