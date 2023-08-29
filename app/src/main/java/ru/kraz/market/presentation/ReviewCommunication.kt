package ru.kraz.market.presentation

import ru.kraz.market.core.Communication
import ru.kraz.market.core.EventWrapper

class ReviewCommunication : Communication.Base<EventWrapper<List<ReviewUi>>>()