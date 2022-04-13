import accountRoutes from './account.route.js'
import categoryRoutes from './category.route.js'
import bookRoutes from './book.route.js'
import searchRoutes from './search.route.js'
import cartRoutes from './cart.route.js'
import sellerRoutes from './seller.route.js'

export default function (app) {
	app.use('/account', accountRoutes)
	app.use('/category', categoryRoutes)
	app.use('/book', bookRoutes)
	app.use('/search', searchRoutes)
	app.use('/cart', cartRoutes)
	app.use('/seller', sellerRoutes)
}
