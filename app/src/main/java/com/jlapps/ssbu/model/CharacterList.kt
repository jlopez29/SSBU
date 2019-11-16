package com.jlapps.ssbu.model

import com.jlapps.ssbu.R

object CharacterList {
    var characters = initChars()

    fun initChars():ArrayList<Character>{
        val list = ArrayList<Character>()
        list.add(
                Character(
                        0,
                        "Villager",
                        "animal_crossing",
                        'E',
                        R.drawable.img_villager,
                        R.drawable.doubutsu,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        1,
                        "Terry",
                        "animal_crossing",
                        'E',
                        R.drawable.img_terry,
                        R.drawable.garou,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        2,
                        "Toon Link",
                        "animal_crossing",
                        'E',
                        R.drawable.img_toon_link,
                        R.drawable.zelda,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        3,
                        "Wario",
                        "animal_crossing",
                        'E',
                        R.drawable.img_wario,
                        R.drawable.wario,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        4,
                        "Wii Fit Trainer",
                        "animal_crossing",
                        'E',
                        R.drawable.img_wii_fit_trainer,
                        R.drawable.wii_fit,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        5,
                        "Wolf",
                        "animal_crossing",
                        'E',
                        R.drawable.img_wolf,
                        R.drawable.starfox,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        6,
                        "Yoshi",
                        "animal_crossing",
                        'E',
                        R.drawable.img_yoshi,
                        R.drawable.yoshi,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        7,
                        "Young Link",
                        "animal_crossing",
                        'E',
                        R.drawable.img_young_link,
                        R.drawable.zelda,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        8,
                        "Zelda",
                        "animal_crossing",
                        'E',
                        R.drawable.img_zelda,
                        R.drawable.zelda,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        list.add(
                Character(
                        9,
                        "Zero Suit Samus",
                        "animal_crossing",
                        'E',
                        R.drawable.img_zero_suit_samus,
                        R.drawable.metroid,
                        CharacterAttributes(60,40,80,20,80,40,20,40)
                )
        )
        return list
    }

    fun getChar(name:String) = characters.find{it.name == name}
}