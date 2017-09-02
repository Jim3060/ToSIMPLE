import dagreD3 from "dagre-d3-webpack";
import * as d3 from "d3";

class Question{
    constructor(title, choices, conditions){
        this.title = title;
        this.choices = choices;
        this.conditions = conditions;
        this.choicesUsed = new Set(); // To record whether this question is open
        this.open = choices.length > 0;
    }

    isOpen(){
        if (this.choices.length > 0 && this.choicesUsed.length == this.choices.length) {
            this.open = false;
        }
        return this.open;
    }

    close() {
        this.open = false;
    }

    link(choices){
        if (choices == undefined){
            this.open = false;
        }else{
            this.choicesUsed = new Set([...choices, ...this.choicesUsed]);
        }
    }

    conditionEqual(q) {
        if (Object.keys(this.conditions).length == 0) {
            return false;
        }
        for (let idx in this.conditions) {
            if (q.conditions[idx] == undefined || q.conditions[idx].length != this.conditions[idx].length) {
                return false;
            }
            else {
                for (let i in this.conditions[idx]) {
                    if (this.conditions[idx][i] != q.conditions[idx][i]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

export default class FlowChart{
    /**
     * constructor - initialize with an array of question
     * @param {Array} questions 
     */
    constructor(questions){
        this.questions = [];
        for (let q of questions){
            let question = new Question(q.questionTitle, q.choices || [], q.showAfter || {});
            this.questions.push(question);
        }
        this.questions.push(new Question("End", [], {}));

        this.g = new dagreD3.graphlib.Graph()
            .setGraph({})
            .setDefaultEdgeLabel(function() { return {}; });

        this.ready = false;
    }

    /**
     * mount the flow chart to DOM
     * @param {String} selector selector of mount point
     */
    mount(selector){
        if (!this.ready) {
            this.generate();
        }

        this.g.nodes().forEach((v) => {
            let node = this.g.node(v);
            // Round the corners of the nodes
            node.rx = node.ry = 5;
        });

        let render = new dagreD3.render();
        let svg = d3.select(selector);
        let svgGroup = svg.append("g");
        render(d3.select(`${selector} g`), this.g);

        let xCenterOffset = 50;/*(svg.attr("width") - this.g.graph().width) / 2;*/
        svgGroup.attr("transform", "translate(" + xCenterOffset + ", 20)");
        svg.attr("height", this.g.graph().height + 40);
    }

    /**
     * Add an edge from one node to another
     * @param {Number} from 
     * @param {Number} to 
     * @param {Array} condition 
     */
    link(from, to, condition){
        this.questions[from].link(condition);
        let choices = [];
        for (let idx of (condition || [])) {
            choices.push(this.questions[from].choices[idx].text);
        }
        this.g.setEdge(from, to, {label: choices.join()});
    }

    isDependant(question1, question2){
        let q1 = question1 < question2 ? question1 : question2;
        let q2 = question1 > question2 ? question1 : question2;

        let conditions = Object.keys(this.questions[q2].conditions);
        for (let c of conditions) {
            if (c == q1) {
                return true;
            }
            else if (c > q1) {
                if (this.isDependant(q1, c)) {
                    return true;
                }
            }
        }
        return false;
    }

    generate(){
        for (let i = 0; i < this.questions.length; i++) {
            let flag = false;
            this.g.setNode(i, {label: this.questions[i].title});

            for (let j = i - 1; j >= 0; j--) {
                if (this.questions[i].conditionEqual(this.questions[j])) {
                    this.link(j, i);
                    this.questions[j].close();
                    flag = true;
                }
            }
            if (flag) {
                continue;
            }

            let conditions = this.questions[i].conditions;
            for (let c in conditions) {
                this.link(c, i, conditions[c]);
            }

            for (let j = 0; j < i; j++) {
                if (this.questions[j].isOpen() && !this.isDependant(j, i)) {
                    this.link(j, i);
                }
            }
        }

        this.ready = true;
    }

}