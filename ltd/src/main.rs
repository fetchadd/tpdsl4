//(prog (stat y = (expr (multExpr (primary 3)) + (multExpr (primary 4)))) (stat print (expr (multExpr (primary x) * (primary [ (expr (multExpr (primary 2))) , (expr (multExpr (primary 3))) , (expr (multExpr (primary 4))) ])))))

extern crate serde;
extern crate docopt;
extern crate ast;

use std::path::Path;
use serde::Deserialize;
use docopt::Docopt;
use std::fs::File;
use std::io::BufReader;
use std::io::prelude::Read;
use ast::AST;


const USAGE: &'static str = "
ltd

Usage:
   ltd -i <input_fie>
   ltd -i <input_fie> -o <outfile>
   ltd -V | --version

Options:
   --help                  show help message
   -i, --in <input>        input file
   -o, --out <output>      output file [default: ./out.dot]
   -V, --version           current version
";

#[derive(Deserialize, Debug)]
struct Args {
    flag_in: String,
    flag_out: String,
    flag_version: bool
}


fn main() -> std::io::Result<()> {
    let args = parse_args();

    let file = File::open(args.flag_in)?;
    let mut buf_reader = BufReader::new(file);
    let mut contents = String::new();
    buf_reader.read_to_string(&mut contents)?;

    contents.chars()
    println!("{}", contents);

    Ok(())
}

fn parse_args() -> Args {
    let args: Args = Docopt::new(USAGE)
        .and_then(|d| d.deserialize())
        .unwrap_or_else(|e| e.exit());

    if args.flag_version {
        println!("1.0.0\n");
        std::process::exit(0);
    }


    if ! Path::new(&args.flag_in).exists() {
        println!("file {} is not existed", args.flag_in);
        std::process::exit(1);
    }

    args
}
