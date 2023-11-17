(()=>{
    let areaItems = document.getElementsByClassName("area-item")
    let allPostBtn = areaItems[0];
    let userPostBtn = areaItems[1];
    let allPostBox = document.getElementById("allPostList");
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
        let currentURL = "/";
        // 새로운 파라미터를 추가할 수 있습니다.
        let newParam1 = `userId=${userId}`;
        let newParam2 = `categoryId=${getSelectedValue()}`;
        // 현재 URL과 새로운 파라미터를 조합하여 새 URL을 생성합니다.
        let newURL = currentURL + "?" + newParam1 + "&" + newParam2;
        window.location.href = newURL;
    });


    (()=>{
        let url = new URL(window.location.href);

        // underline for btn
        let userId = url.searchParams.get("userId");
        if(userId == null) {
            allPostBtn.id = 'selected';
            categoryElement.style.display = "none";
        }
        else
            userPostBtn.id = 'selected';


        // set select option with param
        let categoryId = url.searchParams.get("categoryId")
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

        // 선택된 areaItem 에 밑줄이 생기도록 하는 기능
        // allPostBtn.id = 'selected';
        // userPostBtn.id = 'unselected';


        window.location.href = "/";
    })

    userPostBtn.addEventListener('click', ()=>{

        // 선택된 areaItem 에 밑줄이 생기도록 하는 기능
        // userPostBtn.id = 'selected';
        // allPostBtn.id = 'unselected';

        let currentURL = "/";
        // 새로운 파라미터를 추가할 수 있습니다.
        let newParam1 = `userId=${getUserId()}`;
        let newParam2 = `categoryId=${getSelectedValue()}`;
        // 현재 URL과 새로운 파라미터를 조합하여 새 URL을 생성합니다.
        let newURL = currentURL + "?" + newParam1 + "&" + newParam2;
        window.location.href = newURL;
    })


    // get categories and set selection
    // fetch('/category?' + new URLSearchParams({
    //     userId : document.getElementById("userId").value
    // }))
    //     .then(response => {
    //         // 응답이 성공적으로 돌아오면 JSON 형식으로 파싱
    //         return response.json();
    //     })
    //     .then(data => {
    //         // JSON 데이터를 사용하여 무엇인가를 수행
    //         const selectElement = document.getElementById('categories');
    //         data.forEach(item => {
    //             const option = document.createElement('option');
    //             option.value = item.name; // 옵션의 값을 설정
    //             option.text = item.name; // 옵션의 텍스트 설정
    //             selectElement.appendChild(option);
    //         });
    //         console.log(data);
    //     })
    //     .catch(error => {
    //         // 오류 처리
    //         console.error('오류 발생:', error);
    //     });

})()