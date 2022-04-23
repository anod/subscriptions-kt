package info.anodsplace.subscriptions.app.store

import info.anodsplace.subscriptions.graphql.fragment.GQPayment
import info.anodsplace.subscriptions.graphql.type.period_enum

typealias GQPeriod = period_enum

data class NewMethod(override val id: Int = 0, override val name: String = "", override val objectId: String = "") :
    GQPayment.Method

data class NewSubscription(
    override val id: Int = 0,
    override val objectId: String = "",
    override val created: String = "",
    override val link: String = "",
    override val name: String = "",
    override val tags: List<GQPayment.Subscription.Tag> = emptyList(),
    override val __typename: String = ""
) : GQPayment.Subscription

data class NewPayment(
    override val id: Int = 0,
    override val objectId: String = "",
    override val price: Float = 0f,
    override val currency: String = "",
    override val date: String = "",
    override val dateEnd: String? = null,
    override val method: GQPayment.Method = NewMethod(),
    override val period: GQPeriod = GQPeriod.UNKNOWN__,
    override val periodCycle: Int = 0,
    override val subscription: GQPayment.Subscription = NewSubscription(),
    override val __typename: String = ""
) : GQPayment

data class Subscription(
    private val payment: GQPayment,
    val price: Float,
    val currency: String
) {
    val id: Int
        get() = payment.id
    val name: String
        get() = payment.subscription.name
    val method: String
        get() = payment.method.name
    val originalPrice: Float
        get() = payment.price
    val originalCurrency: String
        get() = payment.currency
    val isConverted: Boolean
        get() = payment.currency != currency

    companion object {
        val empty = Subscription(NewPayment(), 0f, "")
    }
}