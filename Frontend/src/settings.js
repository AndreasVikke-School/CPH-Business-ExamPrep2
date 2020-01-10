const URLs = {
    "Home": "/",
    "Login": "/login",
    "JokesV1": "/JokesV1",
    "NoMatch": "*"
}

function URLSettings() {
    const getURL = (key) => { return URLs[key] }

    return {
        getURL
    }
}
export default URLSettings();


