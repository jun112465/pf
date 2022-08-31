// 즉시실행함수 IIDE
(()=>{
    let newNotice = document.getElementById("new-notice")
    newNotice.addEventListener('click', ()=>{
        createItem().then((value)=>{
            if(value){
                location.assign('/');
                alert("노트가 생성됐습니다.")
            }else{
                alert("이미 생성된 노트가 있습니다.")
            }
        })
        console.log("clicked")
    })

    let createItem = async ()=>{
        const resp = await fetch("/notice/add-note", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
        }).then(result => result.json())
            .then(data => data)

        return resp;
    }

    //date format function
    function dateFormat(date) {
        let month = date.getMonth() + 1;
        let day = date.getDate();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;

        return date.getFullYear() + '-' + month + '-' + day;
    }

    //set contentEditable
    let itemList = document.getElementsByClassName("item");
    if (itemList[1].children[1].innerHTML == dateFormat(new Date())){
        let editBox = document.getElementsByClassName("edit-box")[0];
        editBox.setAttribute("contenteditable", "true");
        editBox.addEventListener("input", ()=>{
            fetch("/notice/update", {
                method: "Post",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    'id' : itemList[1].children[0].value,
                    'memberId' : '',
                    'content' : editBox.innerHTML,
                    'date' : ''
                })
            })
        })
    }

})()