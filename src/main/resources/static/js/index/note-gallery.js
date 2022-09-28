// 즉시실행함수 IIDE
(()=>{
    let slideTitles = document.getElementsByClassName("slide-title")
    for(let i=0; i<slideTitles.length; i++){
        slideTitles[i].addEventListener('focus', ()=>{
            slideTitles[i].nextElementSibling.style.display = "inline-block";
        })
        slideTitles[i].addEventListener('focusout', ()=>{
            setTimeout(()=>{
                slideTitles[i].nextElementSibling.style.display = "none";
            }, 200);
        })
    }

    let newNotices = document.getElementsByClassName("new-item")
    for(let i=0; i<newNotices.length; i++){
        newNotices[i].addEventListener('click', ()=>{
            let category = newNotices[i].children.namedItem("category").value;
            console.log(category)
            createItem(category).then((value)=>{
                if(value){
                    location.assign('/');
                    alert("노트가 생성됐습니다.")
                }else{
                    alert("이미 생성된 노트가 있습니다.")
                }
            })
            console.log("clicked")
        })
    }
    // let newNotice = document.getElementById("new-notice")
    // newNotice.addEventListener('click', ()=>{
    //     let category = newNotice.children.namedItem("category").value;
    //     createItem(category).then((value)=>{
    //         if(value){
    //             location.assign('/');
    //             alert("노트가 생성됐습니다.")
    //         }else{
    //             alert("이미 생성된 노트가 있습니다.")
    //         }
    //     })
    //     console.log("clicked")
    // })

    let createItem = async (categoryId)=>{
        const resp = await fetch("/notice/add-note", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "categoryId" : categoryId
            }),
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

    // add event to edit_or_save btn


    //set contentEditable
    let converter = new showdown.Converter()
    let turndownService = new TurndownService()
    let notices = document.getElementsByClassName("notice")
    let editors = document.getElementsByClassName("editor")
    for (let i=0; i<notices.length; i++){
        // let eb = notices[i].children[2]
        let eb = editors[i].children[0]
        let tar = editors[i].children[1]
        eb.setAttribute("contenteditable", "true");
        eb.addEventListener("input", ()=>{

            console.log(eb.value)
            console.log(eb.textContent)
            tar.innerHTML = converter.makeHtml(eb.value)

            // console.log(eb.innerHTML)
            // console.log(turndownService.turndown(eb.innerText))
            // const markdown = turndownService.turndown(eb.innerText)
            // console.log(markdown)


            fetch("/notice/update", {
                method: "Post",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    'id' : notices[i].children[0].value,
                    'memberId' : '',
                    'content' : eb.value,
                    'date' : ''
                })
            })
        })
    }

    let btns_editOrsave = document.getElementsByClassName("btn_edit_or_save")
    let contents = document.getElementsByClassName("content")
    for(let i=0; i<btns_editOrsave.length; i++){
        btns_editOrsave[i].addEventListener('click', ()=>{
            console.log("btn clicked")
            if (btns_editOrsave[i].value == "edit"){
                btns_editOrsave[i].value = "save"
                contents[i].style.display = "none"
                editors[i].style.display = "flex"

                notices[i].style.width = "640px"

            }else{
                btns_editOrsave[i].value = "edit"
                contents[i].style.display = "block"
                editors[i].style.display = "none"
                contents[i].innerHTML = converter.makeHtml(editors[i].children[0].value)


                notices[i].style.width = "320px"
            }
        })
    }

    // category 변경 기능
    let categories = document.getElementsByClassName("slide-title")
    for(let i=0; i<categories.length; i++){
        console.log("category id : " + categories[i].dataset.id)
        categories[i].addEventListener("input", ()=>{
            fetch("/notice/update-category", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    'id': categories[i].dataset.id,
                    'category': categories[i].innerText,
                })
            })
        })
    }


})()