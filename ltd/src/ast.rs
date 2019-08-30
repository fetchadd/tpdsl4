use std::str::Chars;

pub struct AST<'a> {
    name: &'a str,
    children: Option<Vec<AST<'a>>>,
}


impl<'a> AST {
    fn new(name: &'a str, has_children: bool) -> AST {
        let mut children: Option<Vec<AST<'a>>>;

        if has_children {
            children = Some(Vec::new());
        } else {
            children = None
        }

        AST {
            name,
            children
        }
    }

    fn add_child(&mut self, name: &'a str) {
        let child = AST::new(name, false);

        self.children.get_or_insert(Vec::new()).push(child);
    }
}

pub struct Stack<'l> {
    stack: Vec<&'l str>,
}


impl<'l> Stack<'l> {
    pub fn new() -> Stack {
        Stack { stack: Vec::new()}
    }

    /// Returns The number of elements in the Stack.
    pub fn len(&self) -> usize { self.stack.len() }

    /// Returns `true` if the stack is empty.
    pub fn is_empty(&self) -> bool { self.stack.is_empty() }

    /// Returns the top-most element (if any).
    pub fn top(&self) -> Option<&'l str> {
        if self.is_empty() {
            return None;
        }

        Some(*self.stack.last().unwrap())
    }

    // Used by Parser to insert StackElement::Key elements at the top of the stack.
    fn push(&mut self, value: &'l str) {
        self.stack.push(value);
    }

    // Used by Parser to remove the top-most element of the stack.
    fn pop(&mut self) -> Option<&'l str> {
        self.stack.pop()
    }
}

pub struct Parser<'l, T> {
    buf: T,
    ch: Option<char>,
    stack: Stack<'l>,
    state: ParseState,
}

enum ParserState {
    ParseStart,
    ParseList,
    ParseFinished,
}

use self::ParserState::*;

impl<T: Iterator<Item=char>> Parser<T> {
    fn parse(&mut self)  {
        self.consume();
        self.consume_whitespace();

        while !self.eof() {
            match self.stack {

            }

            self.consume_whitespace();
        }
    }

    fn parse_start(&mut self) {

    }

    fn parse_list(&mut self) {

    }

    fn eof(&self) -> bool {
        self.ch.is_none()
    }

    fn consume(&mut self) {
        self.ch = self.buf.next()
    }

    fn next_char(&mut self) -> Option<char> {
        self.consume();
        self.ch
    }

    fn ch_is(&self, c: char) -> bool {
        self.ch == Some(c)
    }

    fn consume_whitespace(&mut self) {
        while !self.eof() &&
            (
                self.ch_is(' ') ||
                    self.ch_is('\n') ||
                    self.ch_is('\r') ||
                    self.ch_is('\t')
            ) {
            self.consume();
        }
    }
}

