(()=>{
    let areaItems = document.getElementsByClassName("area-item")
    let allPostBtn = areaItems[0];
    let userPostBtn = areaItems[1];

    let categoryElement = document.getElementById("categories");
    // let userPostBox = document.getElementById("userPostList");

    let getSelectedValue = ()=>{
        // select 태그에서 현재 선택된 옵션 요소를 가져오기
        let select = document.getElementById("categories");
        let selectedValue = select.options[select.selectedIndex].value;

        // 선택된 값을 return
        return selectedValue;
    }

    let getUserId = ()=>{
        let userIdElement = document.getElementById("userId")
        return userIdElement.value;
    }


    // Add an event listener to the select element
    const selectElement = document.getElementById("categories");
    selectElement.addEventListener("change", function() {
        // Update the content of the span element
        let currentURL = "/post/get";
        // 새로운 파라미터를 추가할 수 있습니다.
        let newParam1 = `userId=${getUserId()}`;
        let newParam2 = `categoryId=${getSelectedValue()}`;
        // 현재 URL과 새로운 파라미터를 조합하여 새 URL을 생성합니다.
        let newURL = currentURL + "?" + newParam1 + "&" + newParam2;
        window.location.href = newURL;
    });


    // allPosts Or userPosts underline
    (()=>{
        let url = new URL(window.location.href);

        // set underline for area btn with param
        let userId = url.searchParams.get("userId");
        let categoryId = url.searchParams.get("categoryId")

        if(categoryId == null) {
            allPostBtn.id = 'selected';
            categoryElement.style.display = "none";
        }
        else
            userPostBtn.id = 'selected';


        // set select option with param
        if(categoryId != null){}
        const selectElement = document.getElementById("categories");
        if (categoryId) {
            for (let option of selectElement.options) {
                if (option.value === categoryId) {
                    option.selected = true;
                }
            }
        }
    })()

    allPostBtn.addEventListener('click', ()=>{
        window.location.href = "/";
    })

    userPostBtn.addEventListener('click', ()=>{
        // 새로운 파라미터를 추가할 수 있습니다.
        let newParam1 = `userId=${getUserId()}`;
        let newParam2 = `categoryId=${getSelectedValue()}`;
        // 현재 URL과 새로운 파라미터를 조합하여 새 URL을 생성합니다.
        let newURL = "/post/get?" + newParam1 + "&" + newParam2;
        window.location.href = newURL;
    })

})()