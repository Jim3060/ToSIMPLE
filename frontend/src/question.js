export default{
    questionnaireId: 1,
    paperTitle: "paperTitle",
    authorId: 1,
    createDate: 0,
    status: 0,
    questions:[
        {
            questionTitle: "Single Choice",
            type: 0,
            choices:[
                "111", "222", "333"
            ],
            showAfter:{

            }
        },
        {
            questionTitle: "Multiple Choice",
            type: 1,
            choices:[
                "111", "222", "333", "444"
            ],
            limit: 2,
            showAfter:{
                0:[1]
            }
        },
        {
            questionTitle:"Blank",
            type: 2,
            showAfter:{
                0:[2],
                1:[2, 3]
            }
        }
    ]
};