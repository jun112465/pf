(()=> {

    // convert htmlText to Html
    let contents = document.getElementsByClassName("content");
    let targets = document.getElementsByClassName("target");
    for (let i = 0; i < contents.length; i++) {
        // target 의 값이 먼저 바뀌어야한다.
        targets[i].innerHTML = contents[i].textContent;
        contents[i].innerHTML = contents[i].textContent;
    }

    // add edit & save button eventListener
    let posts = document.getElementsByClassName("post");
    let editors = document.getElementsByClassName("editor");
    let btnEditAndSave = document.getElementsByClassName("btn_edit_or_save");
    for (let i = 0; i < btnEditAndSave.length; i++) {
        let content = contents[i];
        let editor = editors[i];
        let btn = btnEditAndSave[i];
        //add post btn 때문에 인덱스 + 1 해줘야 한다.
        let post = posts[i + 1];

        btn.addEventListener("click", () => {
            if (btn.value == "edit") {
                btn.value = "save"
                content.style.display = "none"
                editor.style.display = "flex"

                post.style.width = "800px"
                post.style.minWidth = "800px"

            } else {
                btn.value = "edit"
                content.style.display = "block"
                editor.style.display = "none"


                // targets[i].innerHTML = contents[i].textContent;
                post.style.width = "400px"
                post.style.minWidth = "400px"
            }
        })
    }

    // editing 실시간 변화
    let converter = new showdown.Converter();
    converter.setFlavor('github');
    for (let i = 0; i < editors.length; i++) {
        let content = contents[i];
        let postId = posts[i + 1].children[0].value;
        let editBox = editors[i].children[0];
        let target = targets[i];
        let loading = false

        editBox.setAttribute("contenteditable", "true");
        editBox.addEventListener("input", () => {
            // convert to html
            target.innerHTML = converter.makeHtml(editBox.value);
            content.innerHTML = converter.makeHtml(editBox.value);

            // autoSave
            if (!loading){
                setTimeout(()=>{
                    fetch("/post/update", {
                        method: "Post",
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({
                            'id' : postId,
                            'memberId' : '',
                            'content' : editBox.value,
                            'date' : ''
                        })
                    })
                    loading = !loading
                    console.log("Note Saved!")
                }, 1500)
                loading = !loading
            }
        })
    }
})();