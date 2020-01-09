const URLs = {
    "Home": "/",
    "Login": "/login",
    "Products": "/products",
    "ProductId": "/poroduct/:id",
    "Data": "/data",
    "Contact": "/contact",
    "About": "/about",
    "FAQ": "/faq",
    "Basket": "/cart",
    "Checkout": "/checkout",
    "NoMatch": "*"
}

function URLSettings() {
    const getURL = (key) => { return URLs[key] }

    return {
        getURL
    }
}
export default URLSettings();


