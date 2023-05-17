package com.example.goev.forum

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.goev.database.forum.*
import com.example.goev.database.user.UserData
import com.example.goev.databases.TipsAndKnowledgeDatabase
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ForumViewModel(application: Application) : AndroidViewModel(application) {
    private val forumPostRepository: ForumPostRepository
    private val forumPostLikeRepository: ForumPostLikeRepository
    private val forumPostDislikeRepository: ForumPostDislikeRepository
    private val forumCommentRepository: ForumCommentRepository
    private val forumCommentLikeRepository: ForumCommentLikeRepository
    private val forumCommentDislikeRepository: ForumCommentDislikeRepository
    private val forumHashtagRepository: ForumHashtagRepository
    private val forumRepliesRepository: ForumRepliesRepository
    private val forumPostHashtagRepository: ForumPostHashtagRepository
    private val userRepository: com.example.goev.database.user.UserRepository

    private val _forumDataList = MutableLiveData<List<ForumPost>?>()
    val forumDataList: LiveData<List<ForumPost>?>
        get() = _forumDataList

    private val _searchDataList = MutableLiveData<List<ForumPost>?>()
    val searchDataList: LiveData<List<ForumPost>?>
        get() = _searchDataList

    private val _searchData = MutableLiveData<ForumPost>()
    val searchData: LiveData<ForumPost>
        get() = _searchData

    private val _forumData = MutableLiveData<ForumPost>()
    val forumData: LiveData<ForumPost>
        get() = _forumData

    private val _forumCommentDataList = MutableLiveData<List<ForumPostComment>?>()
    val forumCommentDataList: LiveData<List<ForumPostComment>?>
        get() = _forumCommentDataList

    lateinit var currentLoginUser : UserData



    val forumHashtagRecords = listOf<ForumHashtagData>(
        ForumHashtagData("hashTag1", "EVCharging"),
        ForumHashtagData("hashTag2", "ElectricVehicle"),
        ForumHashtagData("hashTag3", "GreenEnergy"),
        ForumHashtagData("hashTag4", "Sustainability"),
        ForumHashtagData("hashTag5", "RenewableEnergy"),
        ForumHashtagData("hashTag6", "CleanEnergy"),
        ForumHashtagData("hashTag7", "EnvironmentallyFriendly"),
        ForumHashtagData("hashTag8", "EVInfrastructure"),
        ForumHashtagData("hashTag9", "ChargingStation"),
        ForumHashtagData("hashTag10", "Tesla"),
        ForumHashtagData("hashTag11", "EVCommunity")
    )

    val forumPostRecords = listOf<ForumPostData>(
        ForumPostData(
            1,
            1,
            "The Benefits of EV Charging Stations for Businesses",
            "As more and more people switch to electric vehicles (EVs), the demand for EV charging stations is growing rapidly. This presents a great opportunity for businesses to attract and retain customers by offering EV charging services.\n" + "\n" + "EV charging stations are not only convenient for EV owners but also provide several benefits for businesses. Firstly, they can attract new customers who are looking for charging stations, especially as the number of EVs on the road increases. Secondly, businesses can use EV charging stations as a marketing tool to promote their brand as environmentally friendly and socially responsible.\n" + "\n" + "In addition, offering EV charging services can also increase the time customers spend at the business. EV owners typically take longer to charge their vehicles, which means they may spend more time in the vicinity of the charging station. This can lead to increased foot traffic and sales for businesses.",
            LocalDateTime.parse(
                "2023-05-11T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-11T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),

        ForumPostData(
            2,
            1,
            "How Solar Panels Are Revolutionizing the Energy Industry",
            "Solar panels have been around for decades, but recent advancements in technology have made them more efficient and affordable than ever before. Today, solar energy is being used to power homes, businesses, and even entire cities.\n" + "\n" + "The benefits of solar energy are numerous. It is a clean, renewable source of energy that doesn't produce any greenhouse gases or other pollutants. Solar panels can also save you money on your energy bills by reducing your dependence on traditional sources of energy.\n" + "\n" + "Another advantage of solar energy is its versatility. Solar panels can be installed on rooftops, in fields, or even on water. In fact, floating solar panels are becoming increasingly popular in areas with limited land availability.\n" + "\n" + "Despite these benefits, some people are still hesitant to invest in solar energy. They may be concerned about the upfront costs or unsure about the technology. However, with government incentives and the declining cost of solar panels, there has never been a better time to go solar.",
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),

        ForumPostData(
            3,
            2,
            "The Top 5 Electric Cars of 2022",
            "As the world moves towards a greener future, electric vehicles are becoming increasingly popular. With so many options on the market, it can be hard to know which electric car to choose. Here are the top 5 electric cars of 2022:\n" + "\n" + "Tesla Model 3 - The Model 3 is Tesla's most affordable electric car, and it's also one of the best-selling electric cars in the world. With a range of up to 263 miles on a single charge and a 0-60 mph time of just 5.3 seconds, the Model 3 is a great option for those looking for a practical electric car.\n" + "\n" + "Ford Mustang Mach-E - The Mustang Mach-E is Ford's first all-electric SUV, and it's already making waves in the electric car market. With a range of up to 300 miles on a single charge and a 0-60 mph time of just 3.5 seconds in the GT version, the Mustang Mach-E is a powerful and stylish option for those looking for an electric SUV.\n" + "\n" + "Audi e-tron GT - The e-tron GT is Audi's first all-electric sports car, and it's a real head-turner. With a range of up to 238 miles on a single charge and a 0-60 mph time of just 3.1 seconds in the RS version, the e-tron GT is a fast and luxurious electric car.\n" + "\n" + "Volkswagen ID.4 - The ID.4 is Volkswagen's first all-electric SUV, and it's a great option for those looking for a practical and affordable electric car. With a range of up to 250 miles on a single charge and a starting price of just \$39,995, the ID.4 is a great value.\n" + "\n" + "Porsche Taycan - The Taycan is Porsche's first all-electric car, and it's a real contender in the electric car market. With a range of up to 227 miles on a single charge and a 0-60 mph time of just 2.6 seconds in the Turbo S version, the Taycan is a fast and luxurious electric car.",
            LocalDateTime.parse(
                "2023-05-11T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-11T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),

        ForumPostData(
            4,
            2,
            "My Experience Owning a Tesla Model 3",
            "I recently purchased a Tesla Model 3 and I have to say, it's been an amazing experience so far. The car is incredibly sleek and modern, and the technology is truly cutting-edge. The all-electric drivetrain is incredibly smooth and responsive, and the car handles like a dream.\n" + "\n" + "One of the things that I love about owning a Tesla is the convenience of being able to charge the car at home. I installed a charging station in my garage, and it's so nice to wake up every morning with a fully charged car. And when I'm on the road, Tesla's Supercharger network makes it easy to quickly charge up the car and get back on the road.\n" + "\n" + "But perhaps the best thing about owning a Tesla is the environmental impact. By driving an all-electric car, I'm helping to reduce emissions and protect the environment. It feels good to know that I'm doing my part to make the world a better place.\n" + "\n" + "Overall, I'm extremely happy with my Tesla Model 3 and I would highly recommend it to anyone who is looking for a high-tech, environmentally-friendly car.",
            LocalDateTime.parse(
                "2023-05-13T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-13T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),

        ForumPostData(
            5,
            1,
            "The Importance of EV Charging Infrastructure for the Future",
            "Electric vehicles (EVs) are becoming more popular as people look for ways to reduce their carbon footprint and cut down on their reliance on fossil fuels. However, EVs are only as good as the charging infrastructure that supports them. In this post, we will discuss the importance of EV charging infrastructure and why it is essential for the future.\n" + "\n" + "EV charging infrastructure refers to the network of charging stations that are needed to keep EVs on the road. EV owners need access to charging stations at home, at work, and on the road. Without enough charging stations, EV drivers will not be able to travel long distances or use their vehicles for extended periods of time.\n" + "\n" + "One of the biggest barriers to the widespread adoption of EVs is range anxiety, the fear of running out of charge while driving. This anxiety can be eased by the availability of charging stations, as drivers know they can recharge their vehicles when needed. In addition, the availability of charging stations can help increase the resale value of EVs, as potential buyers will be more likely to purchase a vehicle that has easy access to charging stations.\n" + "\n" + "In recent years, there has been a significant increase in the number of EV charging stations around the world. Governments and private companies are investing in the infrastructure needed to support EVs, which will help accelerate the adoption of this technology. By providing more charging stations, we can reduce our dependence on fossil fuels and move towards a more sustainable future.",
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        )
    )

    val forumPostHashtagRecords = listOf<ForumPostHashtagData>(
        ForumPostHashtagData("1hashTag1", "hashTag1", 1),
        ForumPostHashtagData("1hashTag2", "hashTag2", 1),
        ForumPostHashtagData("1hashTag9", "hashTag9", 1),
        ForumPostHashtagData("2hashTag5", "hashTag5", 2),
        ForumPostHashtagData("2hashTag6", "hashTag6", 2),
        ForumPostHashtagData("2hashTag4", "hashTag4", 2),
        ForumPostHashtagData("3hashTag1", "hashTag1", 3),
        ForumPostHashtagData("3hashTag2", "hashTag2", 3),
        ForumPostHashtagData("3hashTag9", "hashTag9", 3),
        ForumPostHashtagData("3hashTag11", "hashTag11", 3),
        ForumPostHashtagData("4hashTag2", "hashTag2", 4),
        ForumPostHashtagData("4hashTag7", "hashTag7", 4),
        ForumPostHashtagData("4hashTag9", "hashTag9", 4),
        ForumPostHashtagData("4hashTag10", "hashTag10", 4),
        ForumPostHashtagData("4hashTag11", "hashTag11", 4),
        ForumPostHashtagData("5hashTag1", "hashTag1", 5),
        ForumPostHashtagData("5hashTag2", "hashTag2", 5),
        ForumPostHashtagData("5hashTag3", "hashTag3", 5),
        ForumPostHashtagData("5hashTag8", "hashTag8", 5),
        ForumPostHashtagData("5hashTag9", "hashTag9", 5),

        )

    val forumPostLikeRecords = listOf<ForumPostLikeData>(
        ForumPostLikeData(
            1, 1, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            2, 1, 2,
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            3, 1, 3,
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            4, 2, 4,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            5, 2, 5,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            6, 2, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            7, 3, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            8, 3, 3,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            9, 3, 4,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            10, 4, 5,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            11, 4, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            12, 5, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostLikeData(
            13, 5, 3,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
    )

    val forumPostDislikeRecords = listOf<ForumPostDislikeData>(
        ForumPostDislikeData(
            1, 1, 4,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            2, 1, 5,
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            3, 2, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            4, 2, 3,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            5, 3, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            6, 3, 5,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            7, 4, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            8, 4, 3,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            9, 5, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
        ForumPostDislikeData(
            10, 5, 4,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ),
    )

    val forumCommentRecords = listOf<ForumCommentData>(
        ForumCommentData(
            1,
            1,
            2,
            "I recently bought an electric car and I'm still figuring out the best places to charge it. Any recommendations for good EV charging stations in the area?",
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            2,
            1,
            3,
            "I highly recommend checking out the charging stations at the nearby shopping mall. They're usually located near the entrance and have plenty of charging spots available. I've used them myself and have never had any issues. Hope this helps!",
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            3, 1, 2, "That's great to hear! Thanks a lot!", LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), LocalDateTime.parse(
                "2023-05-14T17:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            4,
            2,
            4,
            "I installed solar panels on my roof last year and I'm amazed at how much energy they generate. Has anyone else installed solar panels on their home?",
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            5,
            2,
            5,
            "I highly recommend checking out the charging stations at the nearby shopping mall. They're usually located near the entrance and have plenty of charging spots available. I've used them myself and have never had any issues. Hope this helps!Yes, I installed solar panels on my home a few years ago and I've seen a significant decrease in my energy bills. It's a great investment for the environment and your wallet!",
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            6,
            2,
            4,
            "I recommend doing some research on different brands and their efficiency ratings. Also, make sure to find a reputable installer who can help guide you through the process.",
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            7,
            3,
            1,
            "I am considering buying an electric car. Can anyone recommend a good model that is affordable?",
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            8,
            3,
            3,
            "I highly recommend the Nissan Leaf. It's a great electric car and is relatively affordable compared to some of the other models out there.",
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            9, 3, 1, "I agree, the Nissan Leaf is a great option. ", LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            10,
            4,
            1,
            "I just got my Tesla Model 3 and it's amazing! The acceleration is so smooth and the autopilot feature makes driving a breeze.",
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            11,
            4,
            3,
            "I've been thinking about getting a Model 3 myself. How is the range on a single charge?",
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            12,
            4,
            1,
            "The range is fantastic! I can easily go 300+ miles on a single charge. Plus, with Tesla's Supercharger network, charging on long road trips is a breeze.",
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            13,
            5,
            4,
            "I'm planning a road trip in my electric car, does anyone have any tips for finding reliable charging stations along the way?",
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T13:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            14,
            5,
            5,
            "There are a lot of great apps you can use to find EV charging stations, like PlugShare and ChargePoint. You can also check the website for the charging network you plan to use, like Tesla's Supercharger network or the Electrify America network.",
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            LocalDateTime.parse(
                "2023-05-14T15:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
        ForumCommentData(
            15, 5, 4, "Alright, thanks.", LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()
        ),
    )

    val forumRepliesRecords = listOf<ForumRepliesData>(
        ForumRepliesData(1, 1, 1, 2),
        ForumRepliesData(2, 1, 2, 3),
        ForumRepliesData(3, 4, 4, 5),
        ForumRepliesData(4, 4, 5, 6),
        ForumRepliesData(5, 7, 7, 8),
        ForumRepliesData(6, 7, 8, 9),
        ForumRepliesData(7, 10, 10, 11),
        ForumRepliesData(8, 10, 11, 12),
        ForumRepliesData(9, 13, 13, 14),
        ForumRepliesData(10, 13, 14, 15),
    )

    val forumCommentLikeRecords = listOf<ForumCommentLikeData>(
        ForumCommentLikeData(
            1, 1, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            2, 2, 1,
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            3, 3, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            4, 2, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            5, 3, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            6, 3, 3,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            7, 4, 4,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            8, 5, 5,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            9, 1, 6,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentLikeData(
            10, 3, 7,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        )
    )

    val forumCommentDislikeRecords = listOf<ForumCommentDislikeData>(
        ForumCommentDislikeData(
            1, 4, 1,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            2, 5, 1,
            LocalDateTime.parse(
                "2023-05-14T16:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            3, 1, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            4, 4, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            5, 5, 2,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            6, 1, 3,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            7, 2, 4,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            8, 1, 5,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            9, 3, 6,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        ), ForumCommentDislikeData(
            10, 5, 7,
            LocalDateTime.parse(
                "2023-05-14T14:21:25", DateTimeFormatter.ISO_DATE_TIME
            ).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
        )
    )


    init {
        val forumPostDao = TipsAndKnowledgeDatabase.getInstance(application).forumPostDao
        val forumPostLikeDao = TipsAndKnowledgeDatabase.getInstance(application).forumPostLikeDao
        val forumPostDislikeDao = TipsAndKnowledgeDatabase.getInstance(application).forumPostDislikeDao
        val forumCommentDao = TipsAndKnowledgeDatabase.getInstance(application).forumCommentDao
        val forumCommentLikeDao = TipsAndKnowledgeDatabase.getInstance(application).forumCommentLikeDao
        val forumCommentDislikeDao = TipsAndKnowledgeDatabase.getInstance(application).forumCommentDislikeDao
        val forumHashtagDao = TipsAndKnowledgeDatabase.getInstance(application).forumHashtagDao
        val userDao = TipsAndKnowledgeDatabase.getInstance(application).userDAO
        val forumRepliesDao = TipsAndKnowledgeDatabase.getInstance(application).forumRepliesDao
        val forumPostHashtagDao = TipsAndKnowledgeDatabase.getInstance(application).forumPostHashtagDao
        forumPostRepository = ForumPostRepository(forumPostDao)
        forumPostLikeRepository = ForumPostLikeRepository(forumPostLikeDao)
        forumPostDislikeRepository = ForumPostDislikeRepository(forumPostDislikeDao)
        forumCommentRepository = ForumCommentRepository(forumCommentDao)
        forumCommentLikeRepository = ForumCommentLikeRepository(forumCommentLikeDao)
        forumCommentDislikeRepository = ForumCommentDislikeRepository(forumCommentDislikeDao)
        forumHashtagRepository = ForumHashtagRepository(forumHashtagDao)
        forumRepliesRepository = ForumRepliesRepository(forumRepliesDao)
        forumPostHashtagRepository = ForumPostHashtagRepository(forumPostHashtagDao)
        userRepository = com.example.goev.database.user.UserRepository(userDao)
    }

    fun getCurrentLoginUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentLoginedUsed = userRepository.getLoggedInUser()
            Log.d("testing","test")
            if (currentLoginedUsed != null) {
                currentLoginUser = currentLoginedUsed
            }
            withContext(Dispatchers.Main) {

            }

        }
    }

    fun loadMyPostList(){
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postsDataList = forumPostRepository.getAllPost()
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postsHashtagDataList = forumPostHashtagRepository.getAllPostHashtagList()
            val hashtagDataList = forumHashtagRepository.getHashTagList()
            val postCommentList = forumCommentRepository.getAllComment()
            val usersList = userRepository.getAllUsers()
            val forumDataList = postsDataList?.map { post ->
                val userData = usersList.filter{it.id == post.userId} [0]
                val postLikeCount = postLikeList?.count { it.postId == post.postId } ?: 0
                val postDislikeCount = postDislikeList?.count { it.postId == post.postId } ?: 0
                val postCommentCount = postCommentList?.count { it.postId == post.postId } ?: 0
                val postHashtagIdList =
                    postsHashtagDataList!!.filter { it.postId == post.postId }.map { it.hashtagId }
                val postHashtagList =
                    hashtagDataList?.filter { hashtag -> postHashtagIdList!!.any { hashtagId -> hashtagId == hashtag.hashtagId } }
                var status: String = ""
                if (postLikeList?.count { it.postId == post.postId && it.userId == userId } == 1) {
                    status = "like"
                } else if (postDislikeList != null) {
                    if (postDislikeList.count { it.postId == post.postId && it.userId == userId } == 1) {
                        status = "dislike"
                    } else {
                        status = "none"
                    }
                }
                ForumPost(
                    post,
                    userData,
                    postLikeCount,
                    postDislikeCount,
                    postCommentCount,
                    postHashtagList,
                    status,
                    userId
                )
            }?.sortedByDescending { it.forumPostData.updatedAt }

            val myPostList = forumDataList?.filter{it.userId == userId}

            withContext(Dispatchers.Main) {
                _forumDataList.postValue(myPostList)
            }
        }
    }

    fun loadMyLikePostList(){
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postsDataList = forumPostRepository.getAllPost()
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postsHashtagDataList = forumPostHashtagRepository.getAllPostHashtagList()
            val hashtagDataList = forumHashtagRepository.getHashTagList()
            val postCommentList = forumCommentRepository.getAllComment()
            val usersList = userRepository.getAllUsers()
            val forumDataList = postsDataList?.map { post ->
                val userData = usersList.filter{it.id == post.userId} [0]
                val postLikeCount = postLikeList?.count { it.postId == post.postId } ?: 0
                val postDislikeCount = postDislikeList?.count { it.postId == post.postId } ?: 0
                val postCommentCount = postCommentList?.count { it.postId == post.postId } ?: 0
                val postHashtagIdList =
                    postsHashtagDataList!!.filter { it.postId == post.postId }.map { it.hashtagId }
                val postHashtagList =
                    hashtagDataList?.filter { hashtag -> postHashtagIdList!!.any { hashtagId -> hashtagId == hashtag.hashtagId } }
                var status: String = ""
                if (postLikeList?.count { it.postId == post.postId && it.userId == userId } == 1) {
                    status = "like"
                } else if (postDislikeList != null) {
                    if (postDislikeList.count { it.postId == post.postId && it.userId == userId } == 1) {
                        status = "dislike"
                    } else {
                        status = "none"
                    }
                }
                ForumPost(
                    post,
                    userData,
                    postLikeCount,
                    postDislikeCount,
                    postCommentCount,
                    postHashtagList,
                    status,
                    userId
                )
            }?.sortedByDescending { it.forumPostData.updatedAt }

            val myLikePostList = forumDataList?.filter{it.status == "like"}

            withContext(Dispatchers.Main) {
                _forumDataList.postValue(myLikePostList)
            }
        }
    }


    fun loadPostList() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postsDataList = forumPostRepository.getAllPost()
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postsHashtagDataList = forumPostHashtagRepository.getAllPostHashtagList()
            val hashtagDataList = forumHashtagRepository.getHashTagList()
            val postCommentList = forumCommentRepository.getAllComment()
            val usersList = userRepository.getAllUsers()
            val forumDataList = postsDataList?.map { post ->
                val userData = usersList.filter{it.id == post.userId} [0]
                val postLikeCount = postLikeList?.count { it.postId == post.postId } ?: 0
                val postDislikeCount = postDislikeList?.count { it.postId == post.postId } ?: 0
                val postCommentCount = postCommentList?.count { it.postId == post.postId } ?: 0
                val postHashtagIdList =
                    postsHashtagDataList!!.filter { it.postId == post.postId }.map { it.hashtagId }
                val postHashtagList =
                    hashtagDataList?.filter { hashtag -> postHashtagIdList!!.any { hashtagId -> hashtagId == hashtag.hashtagId } }
                var status: String = ""
                if (postLikeList?.count { it.postId == post.postId && it.userId == userId } == 1) {
                    status = "like"
                } else if (postDislikeList != null) {
                    if (postDislikeList.count { it.postId == post.postId && it.userId == userId } == 1) {
                        status = "dislike"
                    } else {
                        status = "none"
                    }
                }
                ForumPost(
                    post,
                    userData,
                    postLikeCount,
                    postDislikeCount,
                    postCommentCount,
                    postHashtagList,
                    status,
                    userId
                )
            }?.sortedByDescending { it.forumPostData.updatedAt }
            withContext(Dispatchers.Main) {
                _forumDataList.postValue(forumDataList)
            }
        }
    }

    fun loadPost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postData = forumPostRepository.getPost(postId)
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postsHashtagDataList = forumPostHashtagRepository.getAllPostHashtagList()
            val hashtagDataList = forumHashtagRepository.getHashTagList()
            val postCommentList = forumCommentRepository.getAllComment()
            val usersList = userRepository.getAllUsers()
            val userData = usersList.filter{it.id == postData.userId} [0]
            val postLikeCount = postLikeList?.count { it.postId == postId } ?: 0
            val postDislikeCount = postDislikeList?.count { it.postId == postId } ?: 0
            val postCommentCount = postCommentList?.count { it.postId == postId } ?: 0
            val postHashtagIdList =
                postsHashtagDataList!!.filter { it.postId == postId }.map { it.hashtagId }
            val postHashtagList =
                hashtagDataList?.filter { hashtag -> postHashtagIdList!!.any { hashtagId -> hashtagId == hashtag.hashtagId } }
            var status: String
            if (postLikeList?.count { it.postId == postId && it.userId == userId }!! >= 1) {
                status = "like"
            } else if (postDislikeList?.count { it.postId == postId && it.userId == userId }!! >= 1) {
                status = "dislike"
            } else {
                status = "none"
            }
            withContext(Dispatchers.Main) {
                _forumData.value = ForumPost(
                    postData,
                    userData,
                    postLikeCount,
                    postDislikeCount,
                    postCommentCount,
                    postHashtagList,
                    status,
                    userId
                )
            }
        }
    }

    fun loadCommentList(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userName =currentLoginUser.userName
            val userId = currentLoginUser.id
            val postCommentList = forumCommentRepository.getAllComment()
            val postCommentLikeList = forumCommentLikeRepository.getAllCommentLikeList()
            val postCommentDislikeList = forumCommentDislikeRepository.getAllCommentDislikeList()
            val postRepliesList = forumRepliesRepository.getAllReplies()
            val usersList = userRepository.getAllUsers()

            val forumCommentList = postCommentList?.map { comment ->
                val commentLikeCount =
                    postCommentLikeList?.count { it.commentId == comment.commentId } ?: 0
                val commentDislikeCount =
                    postCommentDislikeList?.count { it.commentId == comment.commentId } ?: 0
                var isReply: Boolean = true
                if (postRepliesList?.count { it.respondedCommentID == comment.commentId } == 1) {
                    isReply = true
                } else {
                    isReply = false
                }
                var parentCommentId = -1
                var respondedCommentData = ForumCommentData(0, 0, -1, "", 0, 0)
                var repondentUserData = UserData(-1,"","","", ByteArray(0),"","",false,false)
                if (isReply) {
                    respondedCommentData =
                        postCommentList?.filter { it.commentId == postRepliesList?.filter { it.respondedCommentID == comment.commentId }!![0].initialCommentID }!![0]
                    parentCommentId =
                        postRepliesList?.filter { it.respondedCommentID == comment.commentId }!![0].parentCommmentId
                    repondentUserData = usersList.filter{it.id == respondedCommentData.userId }[0]
                }

                val repliesCount =
                    postRepliesList?.count { it.parentCommmentId == comment.commentId } ?: 0

                val userData = usersList.filter{it.id == comment.userId}[0]

                var status =
                    if (postCommentLikeList?.count { it.commentId == comment.commentId && it.userId == userId } == 1) {
                        "like"
                    } else if (postCommentDislikeList?.count { it.commentId == comment.commentId && it.userId == userId } == 1) {
                        "dislike"
                    } else {
                        "none"
                    }
                ForumPostComment(
                    comment,
                    respondedCommentData,
                    repondentUserData,
                    userData,
                    commentLikeCount,
                    commentDislikeCount,
                    repliesCount,
                    status,
                    isReply,
                    parentCommentId,
                    userName
                )
            }
            val commentList =
                forumCommentList?.filter { it.isReply == false && it.forumCommentData.postId == postId }
                    ?.sortedByDescending { it.forumCommentData.updatedAt }
            val repliesData = forumCommentList?.filter { it.isReply == true }
            val forumCommentListSorted = mutableListOf<ForumPostComment>()
            if (commentList != null) {
                if (commentList.size != 0) {
                    for (comment in commentList) {
                        forumCommentListSorted.add(comment)
                        if (comment.noOfReplies > 0) {
                            val repliesDataFiltered =
                                repliesData?.filter { it.respondedCommentData.postId == comment.forumCommentData.postId }
                                    ?.sortedByDescending { it.forumCommentData.updatedAt }
                            if (repliesDataFiltered != null) {
                                forumCommentListSorted.addAll(repliesDataFiltered)
                            }
                        }
                    }
                }
            }


            withContext(Dispatchers.Main) {
                _forumCommentDataList.postValue(forumCommentListSorted)
            }
        }
    }

    fun loadSearchList(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postsDataList = forumPostRepository.getAllPost()
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postsHashtagDataList = forumPostHashtagRepository.getAllPostHashtagList()
            val hashtagDataList = forumHashtagRepository.getHashTagList()
            val postCommentList = forumCommentRepository.getAllComment()
            val usersList = userRepository.getAllUsers()

            val forumDataList = postsDataList?.map { post ->
                val userData = usersList.filter{it.id == post.userId} [0]
                val postLikeCount = postLikeList?.count { it.postId == post.postId } ?: 0
                val postDislikeCount = postDislikeList?.count { it.postId == post.postId } ?: 0
                val postCommentCount = postCommentList?.count { it.postId == post.postId } ?: 0
                val postHashtagIdList =
                    postsHashtagDataList!!.filter { it.postId == post.postId }.map { it.hashtagId }
                val postHashtagList =
                    hashtagDataList?.filter { hashtag -> postHashtagIdList!!.any { hashtagId -> hashtagId == hashtag.hashtagId } }
                var status: String
                if (postLikeList?.count { it.postId == post.postId && it.userId == userId } == 1) {
                    status = "like"
                } else if (postDislikeList?.count { it.postId == post.postId && it.userId == userId } == 1) {
                    status = "dislike"
                } else {
                    status = "none"
                }
                ForumPost(
                    post,
                    userData,
                    postLikeCount,
                    postDislikeCount,
                    postCommentCount,
                    postHashtagList,
                    status,
                    userId
                )
            }?.sortedByDescending { it.forumPostData.updatedAt }

            val searchResult = forumDataList?.filter {
                it.forumPostData.title.contains(
                    search,
                    ignoreCase = true
                ) == true
            }

            withContext(Dispatchers.Main) {
                _searchDataList.postValue(searchResult)
                if (searchResult!!.isEmpty()) {
                    val toast =
                        Toast.makeText(getApplication(), "No Search Result", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }

    }

    fun addPostListLike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            async {
                forumPostLikeRepository.addPostLike(
                    ForumPostLikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPostList()
        }
    }

    fun addPostListDislike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            async {
                forumPostDislikeRepository.addPostDislike(
                    ForumPostDislikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPostList()
        }
    }

    fun deletePostListLike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId =currentLoginUser.id
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postLike =
                postLikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postlike type")
            async {
                forumPostLikeRepository.deletePostLike(
                    postLike
                )
            }.await()
            loadPostList()
        }
    }

    fun deletePostListDislike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postDislike =
                postDislikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postDislike type")
            async { forumPostDislikeRepository.deletePostDislike(postDislike) }.await()
            loadPostList()
        }
    }

    fun postListDisiketoLike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postDislike =
                postDislikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postDislike type")
            async {
                forumPostDislikeRepository.deletePostDislike(postDislike)
                forumPostLikeRepository.addPostLike(
                    ForumPostLikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPostList()
        }
    }

    fun postListLiketoDislike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postLike =
                postLikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postlike type")
            async {
                forumPostLikeRepository.deletePostLike(
                    postLike
                )
                forumPostDislikeRepository.addPostDislike(
                    ForumPostDislikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPostList()
        }
    }

    fun addPostLike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            async {
                forumPostLikeRepository.addPostLike(
                    ForumPostLikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPost(postId)
        }
    }

    fun addPostDislike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            async {
                forumPostDislikeRepository.addPostDislike(
                    ForumPostDislikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPost(postId)
        }
    }

    fun deletePostLike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postLike =
                postLikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postlike type")
            async {
                forumPostLikeRepository.deletePostLike(
                    postLike
                )
            }.await()
            loadPost(postId)
        }
    }

    fun deletePostDislike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postDislike =
                postDislikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postDislike type")
            async { forumPostDislikeRepository.deletePostDislike(postDislike) }.await()
            loadPost(postId)
        }
    }

    fun postDisiketoLike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val postDislikeList = forumPostDislikeRepository.getAllPostDislikeList()
            val postDislike =
                postDislikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postDislike type")
            async {
                forumPostDislikeRepository.deletePostDislike(postDislike)
                forumPostLikeRepository.addPostLike(
                    ForumPostLikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPost(postId)
        }
    }

    fun postLiketoDislike(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId =currentLoginUser.id
            val postLikeList = forumPostLikeRepository.getAllPostLikeList()
            val postLike =
                postLikeList!!.find { it.postId == postId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid postlike type")
            async {
                forumPostLikeRepository.deletePostLike(
                    postLike
                )
                forumPostDislikeRepository.addPostDislike(
                    ForumPostDislikeData(
                        postId = postId, userId = userId, createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadPost(postId)
        }
    }

    fun addCommentLike(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            async {
                forumCommentLikeRepository.addCommentLike(
                    ForumCommentLikeData(
                        commentId = commentId,
                        userId = userId,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadCommentList(postId)
        }
    }

    fun addCommentDislike(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            async {
                forumCommentDislikeRepository.addCommentDislike(
                    ForumCommentDislikeData(
                        commentId = commentId,
                        userId = userId,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadCommentList(postId)
        }
    }

    fun deleteCommentLike(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId =currentLoginUser.id
            val commentLikeList = forumCommentLikeRepository.getAllCommentLikeList()
            val commentLike =
                commentLikeList!!.find { it.commentId == commentId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid commentLike type")
            async {
                forumCommentLikeRepository.deleteCommentLike(
                    commentLike
                )
            }.await()
            loadCommentList(postId)
        }
    }

    fun deleteCommentDislike(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val commentDisLikeList = forumCommentDislikeRepository.getAllCommentDislikeList()
            val commentDisLike =
                commentDisLikeList!!.find { it.commentId == commentId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid comentDislike type")
            async { forumCommentDislikeRepository.deleteCommentDislike(commentDisLike) }.await()
            loadCommentList(postId)
        }
    }

    fun commentDisiketoLike(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val commentDisLikeList = forumCommentDislikeRepository.getAllCommentDislikeList()
            val commentDisLike =
                commentDisLikeList!!.find { it.commentId == commentId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid comentDislike type")
            async {
                val userId =currentLoginUser.id
                forumCommentDislikeRepository.deleteCommentDislike(commentDisLike)
                forumCommentLikeRepository.addCommentLike(
                    ForumCommentLikeData(
                        commentId = commentId,
                        userId = userId,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadCommentList(postId)
        }
    }

    fun commentLiketoDislike(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            val commentLikeList = forumCommentLikeRepository.getAllCommentLikeList()
            val commentLike =
                commentLikeList!!.find { it.commentId == commentId && it.userId == userId }
                    ?: throw IllegalArgumentException("Invalid commentLike type")
            async {
                forumCommentLikeRepository.deleteCommentLike(
                    commentLike
                )
                forumCommentDislikeRepository.addCommentDislike(
                    ForumCommentDislikeData(
                        commentId = commentId,
                        userId = userId,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadCommentList(postId)
        }
    }

    fun addforumReplies(initialCommentId: Int, parentCommentId: Int, postId: Int, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            var forumCommentData = ForumCommentData(0, 0, 1, "", 0, 0)

            async {
                forumCommentRepository.addComment(
                    ForumCommentData(
                        postId = postId,
                        userId = userId,
                        content = content,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
            }.await()
            async {
                forumRepliesRepository.addReplies(
                    ForumRepliesData(
                        parentCommmentId = parentCommentId,
                        initialCommentID = initialCommentId,
                        respondedCommentID = forumCommentRepository.getAllComment()
                            ?.last()!!.commentId
                    )
                )
            }.await()
            loadCommentList(postId)
            loadPost(postId)
        }
    }

    fun addforumComment(
        postId: Int,
        content: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                val userId =currentLoginUser.id
                forumCommentRepository.addComment(
                    ForumCommentData(
                        postId = postId,
                        userId = userId,
                        content = content,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
            }.await()
            loadCommentList(postId)
            loadPost(postId)
        }


    }

    fun addPost(title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = currentLoginUser.id
            async {
                forumPostRepository.addPost(
                    ForumPostData(
                        title = title,
                        content = content,
                        userId = userId,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
            }.await()
        }
        loadPostList()
    }


    //Insert User Data
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertSampleData() {


        viewModelScope.launch(Dispatchers.IO) {
            if (userRepository.getAllUsers()?.count() ?: 0 >= 5 && forumPostRepository.getAllPost()?.count() ?: 0 ==0) {
                async {for (post in forumPostRecords) {
                    forumPostRepository.addPost(post)
                }}.await()
                async { for (hashtag in forumHashtagRecords) {
                    forumHashtagRepository.addHashTag(hashtag)
                }}.await()

                    async {for (comment in forumCommentRecords) {
                    forumCommentRepository.addComment(comment)
                }}.await()

                        async {  for (reply in forumRepliesRecords) {
                    forumRepliesRepository.addReplies(reply)
                }}.await()

                            async { for (postHashtag in forumPostHashtagRecords) {
                    forumPostHashtagRepository.addPostHashtag(postHashtag)
                }}.await()

                                async { for (postLike in forumPostLikeRecords) {
                    forumPostLikeRepository.addPostLike(postLike)
                }}.await()

                                    async {    for (postDislike in forumPostDislikeRecords) {
                    forumPostDislikeRepository.addPostDislike(postDislike)
                }}.await()

                                        async { for (commentLike in forumCommentLikeRecords) {
                    forumCommentLikeRepository.addCommentLike(commentLike)
                }}.await()

                                            async { for (commentDislike in forumCommentDislikeRecords) {
                    forumCommentDislikeRepository.addCommentDislike(commentDislike)
                }}.await()
            }
        }

    }

}