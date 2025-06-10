async function loadDashBoard(){

    try{
        const response = await fetch("/api/dashBoard");

        if (!response.ok) {
            console.error("Server responded with status:", response.status);
            return;
        }
        const data = await response.json();
        console.log("Received data:", data);

            for(const type in data){
                for(const gram in data[type]){
                    const fieldId = type.toLowerCase() + parseFloat(gram);
                    const field = document.getElementById(fieldId);
                    if(field){
                        field.innerText = data[type][gram];
                    }
                }
            }

    }catch(err){
        console.error("Failed to load dashboard data",err);
    }
}
loadDashBoard();