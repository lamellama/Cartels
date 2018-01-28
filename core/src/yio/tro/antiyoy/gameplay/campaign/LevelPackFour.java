package yio.tro.antiyoy.gameplay.campaign;

import yio.tro.antiyoy.gameplay.loading.LoadingParameters;

public class LevelPackFour extends AbstractLevelPack{

    public LevelPackFour(CampaignLevelFactory campaignLevelFactory) {
        super(campaignLevelFactory);
    }


    @Override
    String getLevelFromPack() {
        switch (index) {
            default:
                return "-";
            case 96:
                return "4 4 1 7/15 5 7 2 0 0 10#15 6 7 2 0 0 10#14 7 7 0 0 0 10#31 23 5 3 0 0 10#31 22 5 6 0 0 10#32 21 5 6 0 0 10#16 5 7 2 0 0 10#17 5 7 0 0 0 10#18 5 7 0 0 0 10#19 5 7 0 0 0 10#20 5 7 0 0 0 10#14 9 0 2 0 0 10#13 9 0 3 0 0 10#14 8 0 0 0 0 10#16 13 7 2 0 0 10#15 13 7 2 0 0 10#15 14 7 2 0 0 10#14 15 7 2 0 0 10#14 16 7 0 0 0 10#14 17 7 0 0 0 10#15 17 7 0 0 0 10#16 17 7 0 0 0 10#16 16 3 6 0 0 10#17 16 3 6 0 0 10#18 15 3 6 0 0 10#18 14 3 6 0 0 10#19 13 3 6 0 0 10#19 12 7 2 0 0 10#18 19 7 2 0 0 10#17 19 7 2 0 0 10#16 19 7 0 0 0 10#15 19 7 0 0 0 10#14 19 7 0 0 0 10#13 19 7 0 0 0 10#11 19 7 2 0 0 10#10 19 1 0 0 0 10#10 20 7 2 0 0 10#10 21 7 0 0 0 10#10 22 7 2 0 0 10#11 22 7 2 0 0 10#12 22 7 0 0 0 10#13 22 7 0 0 0 10#14 22 7 2 0 0 10#15 22 7 2 0 0 10#16 22 7 2 0 0 10#17 21 7 0 0 0 10#17 20 7 0 0 0 10#15 18 7 0 0 0 10#17 11 7 2 0 0 10#20 16 4 6 0 0 10#21 15 4 6 0 0 10#21 14 4 6 0 0 10#22 13 4 3 0 0 10#22 12 4 0 0 0 10#23 11 4 0 0 0 10#22 11 4 0 0 0 10#23 10 7 2 0 0 10#23 9 7 2 0 0 10#24 9 7 2 0 0 10#24 8 7 2 0 0 10#25 8 7 2 0 0 10#25 9 7 0 0 0 10#25 10 7 0 0 0 10#26 10 7 2 0 0 10#25 11 7 2 0 0 10#25 12 7 2 0 0 10#25 13 7 2 0 0 10#24 14 7 2 0 0 10#24 15 7 0 0 0 10#23 16 7 2 0 0 10#22 17 7 0 0 0 10#21 17 7 2 0 0 10#21 16 4 6 0 0 10#23 8 7 2 0 0 10#22 8 7 2 0 0 10#20 17 7 0 0 0 10#19 18 7 2 0 0 10#19 6 7 0 0 0 10#17 6 7 0 0 0 10#18 7 7 0 0 0 10#16 7 7 0 0 0 10#15 7 7 0 0 0 10#16 8 7 2 0 0 10#15 8 0 0 0 0 10#17 14 3 0 0 0 10#18 13 3 6 0 0 10#15 16 3 0 0 0 10#16 15 3 0 0 0 10#13 21 7 0 0 0 10#12 20 7 0 0 0 10#12 21 7 0 0 0 10#13 20 7 0 0 0 10#14 20 7 0 0 0 10#15 20 7 0 0 0 10#16 20 7 2 0 0 10#24 12 7 0 0 0 10#23 12 4 0 0 0 10#22 14 4 6 0 0 10#22 16 7 0 0 0 10#23 13 4 6 0 0 10#23 15 7 2 0 0 10#24 13 7 0 0 0 10#25 6 7 2 0 0 10#24 5 7 0 0 0 10#25 4 7 0 0 0 10#24 4 7 2 0 0 10#25 3 7 2 0 0 10#26 3 7 2 0 0 10#27 3 2 6 0 0 10#28 2 2 0 0 0 10#28 3 2 0 0 0 10#29 3 2 6 0 0 10#30 3 7 0 0 0 10#30 4 7 2 0 0 10#28 5 2 0 0 0 10#27 5 7 0 0 0 10#26 5 7 0 0 0 10#27 4 2 6 0 0 10#29 4 2 0 0 0 10#23 4 7 0 0 0 10#32 5 7 2 0 0 10#31 6 7 0 0 0 10#30 7 7 0 0 0 10#30 8 7 2 0 0 10#29 9 7 2 0 0 10#29 10 1 6 0 0 10#28 11 1 6 0 0 10#28 12 1 6 0 0 10#27 13 1 6 0 0 10#27 14 1 3 0 0 10#26 15 7 0 0 0 10#25 16 7 0 0 0 10#26 16 7 0 0 0 10#27 15 7 0 0 0 10#28 14 1 6 0 0 10#29 13 1 6 0 0 10#30 12 1 6 0 0 10#31 11 1 0 1 0 10#31 10 7 0 0 0 10#32 9 7 2 0 0 10#32 8 7 2 0 0 10#32 7 7 2 0 0 10#32 6 7 2 0 0 10#33 4 7 2 0 0 10#32 4 7 2 0 0 10#29 11 1 0 1 0 10#30 9 7 0 0 0 10#31 5 7 2 0 0 10#31 4 7 2 0 0 10#26 13 7 2 0 0 10#30 11 1 0 1 0 10#30 10 1 6 0 0 10#28 19 7 0 0 0 10#27 20 7 0 0 0 10#26 20 7 0 0 0 10#25 20 7 2 0 0 10#23 20 7 0 0 0 10#22 20 2 0 1 0 10#22 19 2 6 0 0 10#21 19 2 3 0 0 10#24 19 7 0 0 0 10#25 19 7 0 0 0 10#26 19 7 0 0 0 10#27 19 7 0 0 0 10#21 20 2 6 0 0 10#20 21 2 6 0 0 10#20 22 2 0 1 0 10#22 22 7 0 0 0 10#23 22 7 0 0 0 10#24 22 7 2 0 0 10#25 22 7 2 0 0 10#26 21 7 2 0 0 10#27 21 7 0 0 0 10#21 21 2 0 1 0 10#22 23 7 0 0 0 10#21 23 2 0 0 0 10#20 23 2 0 1 0 10#19 23 2 6 0 0 10#18 23 2 0 0 0 10#19 22 2 6 0 0 10#26 17 7 0 0 0 10#27 17 7 0 0 0 10#28 16 7 0 0 0 10#28 15 7 0 0 0 10#27 18 7 0 0 0 10#30 23 5 6 0 0 10#30 22 5 6 0 0 10#30 21 5 6 0 0 10#30 20 5 6 0 0 10#31 19 5 0 1 0 10#31 18 7 0 0 0 10#32 17 7 0 0 0 10#33 17 7 0 0 0 10#32 19 5 0 1 0 10#31 21 5 6 0 0 10#33 18 7 2 0 0 10#34 16 7 2 0 0 10#32 14 7 2 0 0 10#31 16 7 0 0 0 10#31 17 7 0 0 0 10#32 16 7 0 0 0 10#33 13 7 0 0 0 10#34 13 7 2 0 0 10#35 13 7 2 0 0 10#34 14 7 0 0 0 10#34 15 7 0 0 0 10#32 13 7 0 0 0 10#33 12 7 2 0 0 10#29 21 5 0 1 0 10#28 21 7 0 0 0 10#32 12 7 2 0 0 10#31 12 1 0 1 0 10#38 8 7 0 0 0 10#39 6 6 6 0 0 10#38 6 6 0 0 0 10#38 5 6 3 0 0 10#37 5 6 6 0 0 10#37 4 6 6 0 0 10#36 4 6 6 0 0 10#35 5 7 0 0 0 10#35 6 7 0 0 0 10#35 7 7 0 0 0 10#35 8 7 2 0 0 10#36 9 7 0 0 0 10#37 8 7 0 0 0 10#36 6 6 0 1 0 10#37 9 7 2 0 0 10#36 10 7 2 0 0 10#35 9 7 0 0 0 10#36 7 6 0 1 0 10#37 6 6 0 1 0 10#34 9 7 2 0 0 10#35 10 7 0 0 0 10#34 6 7 0 0 0 10#33 6 7 0 0 0 10#19 4 7 0 0 0 10#18 4 7 2 0 0 10#17 4 7 2 0 0 10#19 3 7 2 0 0 10#20 3 7 0 0 0 10#16 6 7 0 0 0 10#28 4 2 3 0 0 10#31 7 7 2 0 0 10#28 13 1 6 0 0 10#32 18 7 0 0 0 10#27 16 7 0 0 0 10#25 21 7 0 0 0 10#24 21 7 2 0 0 10#11 21 7 0 0 0 10#17 13 3 6 0 0 10#11 18 1 2 0 0 10#11 17 1 6 0 0 10#11 16 1 0 0 0 10#10 16 1 6 0 0 10#10 17 1 6 0 0 10#9 18 1 6 0 0 10#9 19 1 3 0 0 10#9 20 1 6 0 0 10#8 21 1 6 0 0 10#9 21 7 0 0 0 10#8 20 1 6 0 0 10#10 18 1 6 0 0 10#8 19 1 6 0 0 10#7 19 1 0 0 0 10#13 23 7 2 0 0 10#12 23 7 2 0 0 10#11 23 7 2 0 0 10#24 3 7 2 0 0 10#24 2 7 2 0 0 10#22 4 7 2 0 0 10#22 3 7 2 0 0 10#22 5 7 0 0 0 10#22 6 7 2 0 0 10#21 6 7 2 0 0 10#21 5 7 2 0 0 10#24 6 7 0 0 0 10#23 6 7 0 0 0 10#19 8 7 2 0 0 10#18 9 7 2 0 0 10#17 9 7 2 0 0 10#16 10 7 2 0 0 10#17 10 7 0 0 0 10#16 11 7 0 0 0 10#15 12 7 0 0 0 10#16 12 7 0 0 0 10#18 10 7 2 0 0 10#19 10 7 0 0 0 10#19 11 7 2 0 0 10#15 11 7 2 0 0 10#15 9 7 2 0 0 10#14 11 7 2 0 0 10#13 16 7 0 0 0 10#12 18 7 2 0 0 10#12 15 1 0 0 0 10#17 8 7 2 0 0 10#21 8 7 0 0 0 10#20 10 7 2 0 0 10#21 10 7 2 0 0 10#21 12 4 0 0 0 10#20 14 4 6 0 0 10#18 16 3 6 0 0 10#18 17 3 3 0 0 10#25 7 7 2 0 0 10#30 6 7 0 0 0 10#29 7 7 0 0 0 10#29 6 7 0 0 0 10#28 7 7 0 0 0 10#27 7 7 0 0 0 10#28 8 7 0 0 0 10#27 11 7 0 0 0 10#19 19 7 2 0 0 10#19 21 2 6 0 0 10#34 8 7 0 0 0 10#36 3 6 6 0 0 10#36 2 6 6 0 0 10#35 2 6 6 0 0 10#33 10 7 2 0 0 10#34 12 7 0 0 0 10#39 4 6 6 0 0 10#29 23 5 2 0 0 10#28 23 5 2 0 0 10#31 15 7 0 0 0 10#31 14 7 0 0 0 10#29 15 7 0 0 0 10#30 16 7 0 0 0 10#29 17 7 0 0 0 10#30 18 7 0 0 0 10#29 20 7 0 0 0 10#24 18 7 2 0 0 10#23 18 7 2 0 0 10";
            case 97:
                return "4 4 1 7/21 10 7 7 0 0 10#23 10 4 1 0 0 10#22 11 7 7 0 0 10#21 12 4 1 0 0 10#19 11 7 1 0 0 10#20 11 4 1 0 0 10#14 18 3 1 0 0 10#17 16 3 3 0 0 10#15 17 3 1 0 0 10#14 19 3 1 0 0 10#15 18 3 1 0 0 10#16 17 3 6 0 0 10#16 16 3 0 0 0 10#17 15 3 0 0 0 10#18 15 3 0 0 0 10#17 13 7 0 0 0 10#17 14 7 0 0 0 10#16 14 7 0 0 0 10#14 17 3 2 0 0 10#13 18 3 2 0 0 10#13 19 3 0 0 0 10#13 20 3 0 0 0 10#12 21 3 0 0 0 10#13 21 3 2 0 0 10#14 20 3 1 0 0 10#15 20 3 2 0 0 10#15 19 3 0 0 0 10#16 18 3 6 0 0 10#17 17 3 6 0 0 10#18 16 3 0 0 0 10#10 16 7 0 0 0 10#11 14 7 0 0 0 10#20 14 7 0 0 0 10#20 12 4 1 0 0 10#22 12 7 7 0 0 10#23 13 0 0 0 0 10#21 13 4 1 0 0 10#20 13 7 0 0 0 10#19 13 7 1 0 0 10#19 12 7 1 0 0 10#24 11 7 0 0 0 10#25 11 7 0 0 0 10#24 12 7 0 0 0 10#23 12 4 1 0 0 10#21 11 7 7 0 0 10#22 10 4 1 0 0 10#26 10 7 0 0 0 10#25 10 7 1 0 0 10#24 10 7 1 0 0 10#23 11 4 1 0 0 10#19 10 7 0 0 0 10#21 9 7 7 0 0 10#22 9 4 1 0 0 10#26 9 7 0 0 0 10#25 9 7 1 0 0 10#24 9 7 0 0 0 10#23 9 7 0 0 0 10#20 10 4 3 0 0 10#20 9 4 1 0 0 10#21 8 4 1 0 0 10#22 8 7 7 0 0 10#24 8 7 0 0 0 10#26 8 7 0 0 0 10#25 8 7 0 0 0 10#23 8 4 3 0 0 10#24 7 1 2 0 0 10#25 7 1 0 0 0 10#26 7 1 0 0 0 10#29 10 7 2 0 0 10#28 13 7 2 0 0 10#37 9 7 2 0 0 10#32 6 7 2 0 0 10#33 6 7 2 0 0 10#34 6 7 2 0 0 10#29 22 7 2 0 0 10#29 21 7 2 0 0 10#28 20 7 0 0 0 10#28 19 7 2 0 0 10#30 17 7 0 0 0 10#21 24 7 4 0 0 10#21 22 7 6 0 0 10#21 23 7 6 0 0 10#20 23 7 6 0 0 10#19 23 7 1 0 0 10#16 22 7 0 0 0 10#14 24 7 0 0 0 10#20 24 7 1 0 0 10#21 25 7 0 0 0 10#20 25 7 0 0 0 10#19 25 7 1 0 0 10#16 25 7 2 0 0 10#15 25 7 1 0 0 10#14 25 7 0 0 0 10#12 25 7 2 0 0 10#13 25 7 0 0 0 10#18 20 7 0 0 0 10#19 21 7 1 0 0 10#18 21 7 1 0 0 10#19 22 7 6 0 0 10#17 22 7 0 0 0 10#18 22 7 1 0 0 10#18 23 7 1 0 0 10#24 23 7 2 0 0 10#22 22 7 6 0 0 10#20 22 7 1 0 0 10#23 23 7 0 0 0 10#27 25 7 2 0 0 10#26 23 7 2 0 0 10#27 21 7 0 0 0 10#30 20 7 0 0 0 10#32 18 7 2 0 0 10#32 17 7 2 0 0 10#33 16 7 2 0 0 10#33 15 7 2 0 0 10#34 14 7 0 0 0 10#35 13 7 2 0 0 10#35 12 7 2 0 0 10#32 16 7 0 0 0 10#30 12 7 6 0 0 10#29 16 7 1 0 0 10#29 15 7 1 0 0 10#29 14 7 1 0 0 10#30 13 7 1 0 0 10#35 8 7 0 0 0 10#15 24 7 2 0 0 10#16 24 7 2 0 0 10#19 24 7 1 0 0 10#22 25 7 0 0 0 10#23 25 7 2 0 0 10#25 23 7 2 0 0 10#26 22 7 0 0 0 10#27 22 7 2 0 0 10#31 13 7 1 0 0 10#34 11 7 2 0 0 10#35 9 7 0 0 0 10#34 9 7 6 0 0 10#30 14 7 1 0 0 10#30 15 7 1 0 0 10#31 15 7 1 0 0 10#32 13 7 1 0 0 10#31 11 7 6 0 0 10#31 14 7 1 0 0 10#30 18 7 2 0 0 10#30 19 7 2 0 0 10#26 17 7 2 0 0 10#27 17 7 2 0 0 10#22 19 7 4 0 0 10#21 20 7 6 0 0 10#9 19 7 2 0 0 10#10 18 7 2 0 0 10#10 17 7 2 0 0 10#14 10 7 1 0 0 10#11 13 7 3 0 0 10#12 12 7 2 0 0 10#13 11 7 1 0 0 10#11 11 7 6 0 0 10#11 12 7 6 0 0 10#10 13 7 6 0 0 10#10 14 7 6 0 0 10#10 15 7 6 0 0 10#9 16 7 6 0 0 10#11 16 7 2 0 0 10#11 15 7 0 0 0 10#12 14 7 2 0 0 10#12 13 7 2 0 0 10#13 12 7 2 0 0 10#14 11 7 1 0 0 10#26 25 7 2 0 0 10#27 23 7 2 0 0 10#30 22 7 2 0 0 10#30 21 7 2 0 0 10#31 20 7 2 0 0 10#31 19 7 0 0 0 10#31 18 7 0 0 0 10#31 17 7 0 0 0 10#33 14 7 0 0 0 10#34 13 7 0 0 0 10#34 12 7 0 0 0 10#35 11 7 2 0 0 10#35 10 7 2 0 0 10#36 9 7 2 0 0 10#36 8 7 2 0 0 10#36 7 7 0 0 0 10#35 7 7 2 0 0 10#34 7 7 2 0 0 10#31 9 7 2 0 0 10#32 9 7 0 0 0 10#32 8 7 0 0 0 10#33 7 7 0 0 0 10#33 8 7 0 0 0 10#34 8 7 0 0 0 10#33 9 7 6 0 0 10#32 10 7 3 0 0 10#31 10 7 6 0 0 10#30 10 7 2 0 0 10#30 11 7 0 0 0 10#29 12 7 2 0 0 10#29 13 7 0 0 0 10#28 14 7 0 0 0 10#28 15 7 2 0 0 10#27 16 7 2 0 0 10#28 16 7 1 0 0 10#28 17 7 2 0 0 10#28 18 7 2 0 0 10#27 19 7 2 0 0 10#27 20 7 0 0 0 10#26 21 7 0 0 0 10#25 22 7 0 0 0 10#24 22 7 0 0 0 10#23 21 7 6 0 0 10#22 21 7 6 0 0 10#21 21 7 3 0 0 10#20 21 7 6 0 0 10#20 20 7 2 0 0 10#19 20 7 2 0 0 10#19 19 7 2 0 0 10#21 14 7 0 0 0 10#20 15 7 2 0 0 10#19 15 7 0 0 0 10#19 14 7 0 0 0 10#18 14 7 1 0 0 10#18 13 7 1 0 0 10#18 12 7 0 0 0 10#18 11 7 2 0 0 10#18 10 7 2 0 0 10#19 9 7 0 0 0 10#19 8 7 2 0 0 10#20 8 7 0 0 0 10#21 7 7 0 0 0 10#22 7 4 1 0 0 10#24 6 1 2 0 0 10#25 6 1 2 0 0 10#27 6 1 3 0 0 10#27 7 1 0 0 0 10#28 8 7 2 0 0 10#27 9 7 2 0 0 10#27 10 7 0 0 0 10#26 11 7 0 0 0 10#25 12 0 0 0 0 10#25 13 0 6 0 0 10#24 13 0 6 0 0 10#24 14 0 3 0 0 10#23 14 0 6 0 0 10#21 26 7 2 0 0 10#22 26 7 2 0 0 10#23 26 7 2 0 0 10#24 26 7 0 0 0 10#25 26 7 2 0 0 10#26 26 7 0 0 0 10#27 26 7 0 0 0 10#20 26 7 0 0 0 10#19 26 7 0 0 0 10#17 26 7 2 0 0 10#16 26 7 2 0 0 10#15 26 7 1 0 0 10#14 26 7 0 0 0 10#13 26 7 0 0 0 10#12 26 7 0 0 0 10#11 26 7 2 0 0 10#10 26 7 2 0 0 10#9 27 7 0 0 0 10#10 27 7 0 0 0 10#11 27 7 0 0 0 10#12 27 7 0 0 0 10#13 27 7 0 0 0 10#14 27 7 0 0 0 10#15 27 7 0 0 0 10#16 27 7 0 0 0 10#17 27 7 0 0 0 10#19 27 7 0 0 0 10#20 27 7 0 0 0 10#21 27 7 0 0 0 10#22 27 7 0 0 0 10#23 27 7 0 0 0 10#24 27 7 0 0 0 10#25 27 7 0 0 0 10#26 27 7 0 0 0 10#27 27 7 0 0 0 10";
            case 98:
                return "4 4 1 7/20 17 7 0 0 0 10#25 18 7 2 0 0 10#33 11 7 2 0 0 10#32 14 6 3 0 0 10#32 15 6 4 0 0 10#29 13 7 0 0 0 10#29 14 7 0 0 0 10#29 15 7 0 0 0 10#27 16 7 2 0 0 10#18 20 1 6 0 0 10#20 18 7 2 0 0 10#14 21 1 6 0 0 10#14 20 1 0 0 0 10#10 15 7 0 0 0 10#11 14 7 0 0 0 10#16 6 2 6 0 0 10#17 6 2 6 0 0 10#17 9 7 2 0 0 10#15 10 7 2 0 0 10#18 7 2 6 0 0 10#29 7 5 0 0 0 10#26 8 7 0 0 0 10#26 9 7 0 0 0 10#27 9 7 0 0 0 10#28 9 7 0 0 0 10#29 9 7 0 0 0 10#30 6 5 0 0 0 10#31 6 5 0 0 0 10#31 9 7 0 0 0 10#32 9 7 2 0 0 10#32 10 7 2 0 0 10#33 10 7 0 0 0 10#33 9 5 6 0 0 10#35 9 5 6 0 0 10#34 6 5 6 0 0 10#32 5 5 2 0 0 10#34 5 5 2 0 0 10#34 9 5 6 0 0 10#32 7 5 0 0 0 10#31 7 5 6 0 0 10#32 6 5 0 0 0 10#33 5 5 2 0 0 10#33 6 5 6 0 0 10#33 8 5 0 0 0 10#34 8 5 6 0 0 10#35 8 5 6 0 0 10#35 7 5 3 0 0 10#34 7 5 6 0 0 10#33 7 5 0 0 0 10#32 8 5 0 0 0 10#31 8 7 2 0 0 10#30 9 7 2 0 0 10#29 10 7 7 0 0 10#28 10 7 0 0 0 10#19 4 3 0 0 0 10#20 4 3 0 0 0 10#21 4 3 0 0 0 10#20 5 3 0 0 0 10#21 5 3 0 0 0 10#22 4 3 6 0 0 10#24 4 7 0 0 0 10#23 4 3 3 0 0 10#24 3 3 6 0 0 10#25 3 7 0 0 0 10#26 3 3 0 0 0 10#26 4 3 0 0 0 10#27 4 3 0 0 0 10#27 5 3 6 0 0 10#23 6 7 2 0 0 10#24 6 7 2 0 0 10#24 7 7 0 0 0 10#25 6 7 0 0 0 10#28 6 3 3 0 0 10#27 6 3 6 0 0 10#26 6 3 0 0 0 10#26 5 3 0 0 0 10#25 5 7 0 0 0 10#23 5 7 2 0 0 10#22 5 3 6 0 0 10#22 6 7 2 0 0 10#21 6 7 0 0 0 10#20 7 7 0 0 0 10#20 8 7 0 0 0 10#19 8 7 0 0 0 10#19 9 7 0 0 0 10#18 10 7 0 0 0 10#19 10 7 0 0 0 10#15 8 2 6 0 0 10#16 7 2 3 0 0 10#15 7 2 0 0 0 10#14 8 7 0 0 0 10#13 10 2 0 0 0 10#14 9 7 0 0 0 10#17 7 2 6 0 0 10#16 8 2 6 0 0 10#15 9 7 2 0 0 10#14 10 7 0 0 0 10#13 11 7 0 0 0 10#11 13 7 0 0 0 10#10 13 7 2 0 0 10#10 12 7 2 0 0 10#9 12 7 2 0 0 10#10 11 7 0 0 0 10#11 10 7 0 0 0 10#12 9 7 0 0 0 10#13 7 2 0 0 0 10#14 6 2 0 0 0 10#14 7 2 0 0 0 10#13 8 2 0 0 0 10#13 9 2 0 0 0 10#12 10 7 0 0 0 10#12 11 2 0 0 0 10#12 12 7 0 0 0 10#12 13 7 7 0 0 10#13 13 7 2 0 0 10#21 21 4 6 0 0 10#22 21 4 6 0 0 10#21 22 4 6 0 0 10#23 19 4 0 0 0 10#23 20 7 0 0 0 10#22 20 4 0 0 0 10#22 19 4 0 0 0 10#23 18 7 0 0 0 10#26 15 7 2 0 0 10#25 16 7 0 0 0 10#24 17 7 2 0 0 10#22 15 7 2 0 0 10#22 14 7 2 0 0 10#21 17 7 0 0 0 10#21 18 7 0 0 0 10#20 19 7 0 0 0 10#20 20 4 0 0 0 10#19 21 4 6 0 0 10#19 22 4 6 0 0 10#22 16 7 2 0 0 10#24 14 7 0 0 0 10#25 14 7 0 0 0 10#24 15 7 0 0 0 10#24 16 7 0 0 0 10#23 17 7 2 0 0 10#22 18 4 0 0 0 10#20 21 4 3 0 0 10#21 20 4 6 0 0 10#21 19 4 0 0 0 10#22 17 7 2 0 0 10#23 15 7 7 0 0 10#23 14 7 2 0 0 10#17 16 7 7 0 0 10#15 18 7 0 0 0 10#15 19 1 0 0 0 10#16 16 7 0 0 0 10#16 17 7 0 0 0 10#16 18 7 0 0 0 10#16 21 1 6 0 0 10#18 19 1 6 0 0 10#19 17 7 2 0 0 10#19 16 7 0 0 0 10#17 17 7 0 0 0 10#17 18 1 0 0 0 10#16 19 1 0 0 0 10#15 20 1 0 0 0 10#15 21 1 6 0 0 10#15 22 1 6 0 0 10#16 22 1 6 0 0 10#17 21 1 6 0 0 10#17 20 1 3 0 0 10#18 18 1 0 0 0 10#18 17 7 0 0 0 10#18 16 7 2 0 0 10#18 15 7 2 0 0 10#16 15 7 2 0 0 10#17 15 7 0 0 0 10#18 14 7 0 0 0 10#24 8 7 0 0 0 10#23 8 7 0 0 0 10#22 8 7 0 0 0 10#21 9 7 0 0 0 10#20 10 7 2 0 0 10#19 13 7 0 0 0 10#20 13 7 0 0 0 10#21 13 7 0 0 0 10#22 13 7 0 0 0 10#24 9 7 0 0 0 10#30 12 7 0 0 0 10#29 12 7 0 0 0 10#27 13 7 2 0 0 10#26 13 7 0 0 0 10#25 13 7 0 0 0 10#24 13 7 2 0 0 10#24 12 7 2 0 0 10#23 12 7 0 0 0 10#23 11 7 2 0 0 10#21 10 7 2 0 0 10#20 11 7 7 0 0 10#19 11 7 2 0 0 10#18 11 7 2 0 0 10#17 11 7 0 0 0 10#16 12 7 0 0 0 10#15 12 7 0 0 0 10#14 13 7 0 0 0 10#13 14 7 2 0 0 10#13 15 7 0 0 0 10#12 16 7 0 0 0 10#8 21 0 6 0 0 10#9 20 0 6 0 0 10#10 19 0 4 0 0 10#10 20 0 4 0 0 10#28 23 7 2 0 0 10#28 22 7 2 0 0 10#23 22 7 0 0 0 10#24 22 7 2 0 0 10#33 15 6 0 0 0 10#32 16 6 0 0 0 10#26 20 7 0 0 0 10#30 17 7 2 0 0 10#29 17 7 2 0 0 10#27 20 7 2 0 0 10#28 20 7 2 0 0 10#30 18 7 0 0 0 10#29 19 7 2 0 0 10#27 21 7 0 0 0 10#26 21 7 0 0 0 10#25 22 7 2 0 0 10#29 20 7 0 0 0 10#30 19 7 0 0 0 10#31 17 7 2 0 0 10#31 18 7 2 0 0 10#31 19 7 2 0 0 10#28 21 7 0 0 0 10#26 22 7 2 0 0 10#26 19 7 2 0 0 10#26 18 7 2 0 0 10#27 17 7 0 0 0 10#29 16 7 0 0 0 10#30 16 7 0 0 0 10#31 16 7 4 0 0 10#15 15 7 2 0 0 10#27 19 7 2 0 0 10#27 18 7 0 0 0 10#28 17 7 2 0 0 10#28 16 7 0 0 0 10#28 15 7 2 0 0 10#28 13 7 2 0 0 10#28 12 7 2 0 0 10#28 11 7 2 0 0 10#27 11 7 2 0 0 10#27 10 7 0 0 0 10#26 10 7 2 0 0 10#25 10 7 0 0 0 10#24 10 7 2 0 0 10#23 10 7 7 0 0 10#22 11 7 0 0 0 10#21 11 7 0 0 0 10#20 12 7 0 0 0 10#19 12 7 0 0 0 10#18 13 7 0 0 0 10#17 13 7 0 0 0 10#16 14 7 0 0 0 10#14 15 7 2 0 0 10#13 16 7 0 0 0 10#13 17 7 2 0 0 10#12 17 7 0 0 0 10#12 18 7 2 0 0 10#9 21 0 6 0 0 10#7 21 0 3 0 0 250#8 20 0 6 0 0 10#11 18 7 2 0 0 10#11 19 7 0 0 0 10#11 20 7 2 0 0 10#11 21 7 2 0 0 10";
            case 99:
                return "4 4 1 7/19 19 7 0 0 0 10#18 19 7 0 0 0 10#18 18 7 7 0 0 10#19 17 7 0 0 0 10#20 15 7 0 0 0 10#20 16 7 7 0 0 10#20 17 7 0 0 0 10#21 17 7 0 0 0 10#20 18 7 0 0 0 10#25 11 7 0 0 0 10#22 14 7 7 0 0 10#22 15 7 0 0 0 10#22 13 7 0 0 0 10#23 13 7 0 0 0 10#24 11 7 0 0 0 10#24 12 7 7 0 0 10#31 13 7 2 0 0 10#19 14 7 0 0 0 10#21 12 7 1 0 0 10#19 15 7 1 0 0 10#20 14 7 1 0 0 10#22 10 7 0 0 0 10#20 11 2 0 0 0 10#20 12 7 0 0 0 10#19 13 7 1 0 0 10#20 13 7 0 0 0 10#21 13 7 1 0 0 10#22 12 7 0 0 0 10#22 11 7 1 0 0 10#21 11 2 0 0 0 10#21 10 2 3 0 0 10#22 9 2 0 0 0 10#23 9 7 1 0 0 10#24 9 7 1 0 0 10#27 11 1 6 0 0 10#22 17 7 1 0 0 10#24 16 7 7 0 0 10#25 14 7 0 0 0 10#26 12 1 0 0 0 10#26 13 7 0 0 0 10#25 13 7 1 0 0 10#23 15 7 7 0 0 10#23 16 7 1 0 0 10#23 17 7 0 0 0 10#21 20 7 1 0 0 10#21 21 0 3 0 0 10#22 20 7 0 0 0 10#21 18 7 0 0 0 10#24 17 7 0 0 0 10#26 15 7 1 0 0 10#18 17 7 1 0 0 10#18 16 7 0 0 0 10#18 14 7 0 0 0 10#18 13 7 0 0 0 10#19 12 2 0 0 0 10#19 11 2 0 0 0 10#21 9 2 6 0 0 10#22 8 2 0 0 0 10#23 8 2 0 0 0 10#24 8 7 0 0 0 10#25 8 7 1 0 0 10#24 10 7 0 0 0 10#27 14 7 0 0 0 10#26 14 7 1 0 0 10#25 15 7 0 0 0 10#24 15 7 0 0 0 10#24 14 7 1 0 0 10#24 13 7 0 0 0 10#27 10 1 6 0 0 10#28 10 1 6 0 0 10#28 11 1 3 0 0 10#28 12 1 0 0 0 10#28 13 1 0 0 0 10#27 13 1 0 0 0 10#22 21 0 0 0 0 10#21 22 0 6 0 0 10#20 22 0 6 0 0 10#20 21 0 0 0 0 10#20 20 7 1 0 0 10#21 19 7 0 0 0 10#22 18 7 1 0 0 10#23 18 7 1 0 0 10#23 19 7 0 0 0 10#15 20 7 2 0 0 10#14 20 7 2 0 0 10#12 18 7 0 0 0 10#11 20 7 2 0 0 10#16 27 7 0 0 0 10#10 27 7 0 0 0 10#12 27 7 0 0 0 10#13 27 7 0 0 0 10#15 27 7 0 0 0 10#14 27 7 0 0 0 10#15 26 7 0 0 0 10#12 25 7 0 0 0 10#11 24 7 1 0 0 10#11 23 7 1 0 0 10#12 26 7 0 0 0 10#13 26 7 1 0 0 10#14 26 7 1 0 0 10#13 25 7 0 0 0 10#13 24 7 1 0 0 10#12 22 7 1 0 0 10#12 21 7 0 0 0 10#12 23 7 1 0 0 10#12 24 7 1 0 0 10#33 2 7 1 0 0 10#34 2 7 1 0 0 10#33 3 7 0 0 0 10#33 4 7 2 0 0 10#33 5 7 2 0 0 10#33 6 7 0 0 0 10#36 0 7 0 0 0 10#36 1 7 2 0 0 10#36 2 7 2 0 0 10#35 5 7 0 0 0 10#34 7 7 2 0 0 10#33 8 7 0 0 0 10#10 26 7 2 0 0 10#10 25 7 1 0 0 10#10 24 7 1 0 0 10#10 23 7 2 0 0 10#11 22 7 0 0 0 10#11 21 7 2 0 0 10#12 20 7 0 0 0 10#12 19 7 2 0 0 10#13 17 7 0 0 0 10#18 27 7 0 0 0 10#17 27 7 0 0 0 10#17 26 7 2 0 0 10#16 26 7 0 0 0 10#16 25 7 0 0 0 10#15 25 7 2 0 0 10#14 25 7 0 0 0 10#14 24 7 2 0 0 10#14 23 7 2 0 0 10#13 23 7 2 0 0 10#13 22 7 2 0 0 10#13 21 7 0 0 0 10#13 20 7 0 0 0 10#13 19 7 0 0 0 10#13 18 7 0 0 0 10#14 17 7 0 0 0 10#14 16 7 2 0 0 10#14 15 7 2 0 0 10#15 14 7 2 0 0 10#31 6 7 0 0 0 10#30 4 7 0 0 0 10#29 3 7 2 0 0 10#29 2 7 2 0 0 10#28 1 7 2 0 0 10#27 0 7 0 0 0 10#28 0 7 0 0 0 10#29 0 7 0 0 0 10#30 0 7 0 0 0 10#29 1 7 0 0 0 10#30 1 7 0 0 0 10#30 2 7 0 0 0 10#31 2 7 2 0 0 10#31 3 7 2 0 0 10#31 4 7 0 0 0 10#32 4 7 2 0 0 10#31 5 7 0 0 0 10#32 6 7 0 0 0 10#32 7 7 0 0 0 10#32 9 7 2 0 0 10#32 10 7 2 0 0 10#32 11 7 0 0 0 10#32 12 7 2 0 0 10#33 11 7 0 0 0 10#33 10 7 2 0 0 10#34 8 7 0 0 0 10#34 6 7 0 0 0 10#35 4 7 0 0 0 10#35 0 7 0 0 0 10#32 0 7 0 0 0 10#33 0 7 0 0 0 10#34 0 7 0 0 0 10#34 1 7 1 0 0 10#35 1 7 1 0 0 10#35 2 7 0 0 0 10#35 3 7 0 0 0 10";
            case 100:
                return "4 4 1 7/21 3 7 2 0 0 10#20 3 7 2 0 0 10#20 5 7 0 0 0 10#21 4 7 2 0 0 10#22 4 7 0 0 0 10#21 5 7 1 0 0 10#22 5 7 0 0 0 10#23 4 7 0 0 0 10#24 4 7 2 0 0 10#24 5 7 2 0 0 10#28 5 7 0 0 0 10#36 8 5 6 0 0 10#36 9 5 3 0 0 10#11 18 7 0 0 0 10#18 11 7 0 0 0 10#31 12 7 2 0 0 10#31 11 7 2 0 0 10#29 11 7 2 0 0 10#30 10 7 0 0 0 10#30 9 7 0 0 0 10#30 13 7 0 0 0 10#31 13 7 0 0 0 10#32 6 7 0 0 0 10#33 6 7 0 0 0 10#32 9 7 0 0 0 10#33 9 5 0 0 0 10#32 8 7 2 0 0 10#26 20 4 6 0 0 10#22 22 7 2 0 0 10#23 22 7 2 0 0 10#24 22 7 1 0 0 10#23 23 7 1 0 0 10#24 23 7 2 0 0 10#25 23 7 2 0 0 10#26 23 4 0 0 0 10#28 17 7 0 0 0 10#27 17 7 0 0 0 10#28 14 7 0 0 0 10#28 15 7 1 0 0 10#26 16 7 2 0 0 10#27 14 7 1 0 0 10#27 12 7 2 0 0 10#27 13 7 1 0 0 10#25 13 3 6 0 0 10#25 17 7 2 0 0 10#24 16 7 0 0 0 10#24 15 7 0 0 0 10#23 15 3 0 0 0 10#21 17 7 2 0 0 10#24 18 7 0 0 0 10#23 20 7 2 0 0 10#22 20 7 2 0 0 10#20 21 7 2 0 0 10#22 18 7 1 0 0 10#21 18 7 0 0 0 10#20 20 7 0 0 0 10#19 20 7 0 0 0 10#18 21 7 2 0 0 10#15 22 7 2 0 0 10#17 21 7 0 0 0 10#16 22 7 2 0 0 10#13 22 7 2 0 0 10#14 21 7 0 0 0 10#15 21 7 0 0 0 10#12 22 7 2 0 0 10#11 22 7 2 0 0 10#12 17 7 0 0 0 10#10 21 7 0 0 0 10#13 20 7 0 0 0 10#15 18 7 0 0 0 10#16 18 7 0 0 0 10#18 17 7 0 0 0 10#19 17 7 0 0 0 10#19 16 7 0 0 0 10#20 16 7 2 0 0 10#21 15 7 2 0 0 10#20 14 7 0 0 0 10#21 14 7 0 0 0 10#20 12 7 0 0 0 10#18 15 7 2 0 0 10#18 14 7 0 0 0 10#16 13 7 0 0 0 10#21 12 7 1 0 0 10#21 13 7 0 0 0 10#23 12 7 0 0 0 10#25 10 7 0 0 0 10#23 10 7 2 0 0 10#23 8 7 2 0 0 10#27 7 7 2 0 0 10#25 7 7 2 0 0 10#24 8 7 2 0 0 10#25 8 7 0 0 0 10#26 8 7 2 0 0 10#27 8 7 0 0 0 10#16 9 7 0 0 0 10#17 8 7 0 0 0 10#18 4 1 6 0 0 10#19 4 7 2 0 0 10#17 7 1 4 0 0 10#16 7 1 6 0 0 10#16 6 1 3 0 0 10#29 20 4 6 0 0 10#29 21 4 0 0 0 10#28 22 4 0 0 0 10#27 22 4 6 0 0 10#26 22 4 2 0 0 10#27 21 4 6 0 0 10#27 20 4 6 0 0 10#28 20 4 3 0 0 10#30 19 4 6 0 0 10#30 20 4 0 0 0 10#29 19 4 0 0 0 10#29 18 7 0 0 0 10#29 17 7 2 0 0 10#34 11 5 6 0 0 10#32 11 5 2 0 0 10#33 12 5 2 0 0 10#34 12 5 0 0 0 10#35 11 5 6 0 0 10#35 10 5 6 0 0 10#34 10 5 6 0 0 10#28 16 7 2 0 0 10#29 16 7 2 0 0 10#30 15 7 2 0 0 10#31 14 7 2 0 0 10#32 13 7 2 0 0 10#32 12 7 0 0 0 10#33 11 5 0 0 0 10#33 10 5 6 0 0 10#34 9 5 6 0 0 10#34 8 5 6 0 0 10#34 7 5 0 0 0 10#33 7 5 0 0 0 10#32 7 7 0 0 0 10#31 7 7 0 0 0 10#30 8 2 6 0 0 10#25 14 7 0 0 0 10#26 14 7 0 0 0 10#26 15 7 2 0 0 10#27 16 7 1 0 0 10#25 18 7 0 0 0 10#24 19 7 1 0 0 10#23 19 7 2 0 0 10#22 19 7 2 0 0 10#21 19 7 0 0 0 10#20 19 7 1 0 0 10#19 19 7 2 0 0 10#18 20 7 2 0 0 10#17 20 7 2 0 0 10#16 20 7 0 0 0 10#16 19 7 0 0 0 10#15 19 7 0 0 0 10#14 19 7 0 0 0 10#24 12 3 4 0 0 10#25 11 7 0 0 0 10#26 11 7 0 0 0 10#27 10 7 1 0 0 10#27 9 7 0 0 0 10#28 9 7 0 0 0 10#29 8 2 4 0 0 10#21 10 7 0 0 0 10#21 11 7 0 0 0 10#22 11 7 1 0 0 10#22 12 7 0 0 0 10#22 13 7 0 0 0 10#23 13 3 6 0 0 10#23 7 7 0 0 0 10#24 6 7 0 0 0 10#25 6 7 0 0 0 10#26 6 7 2 0 0 10#27 6 7 1 0 0 10#28 6 7 0 0 0 10#18 6 7 0 0 0 10#19 6 7 1 0 0 10#18 7 7 0 0 0 10#19 7 7 0 0 0 10#19 8 7 0 0 0 10#20 10 7 0 0 0 10#14 17 7 2 0 0 10#19 11 7 0 0 0 10#17 13 7 2 0 0 10#15 15 7 2 0 0 10#16 15 7 2 0 0 10#18 12 7 2 0 0 10#15 16 7 0 0 0 10#16 14 7 0 0 0 10#17 14 7 0 0 0 10#24 14 3 6 0 0 10#24 13 3 3 0 0 10#23 14 3 0 0 0 10#30 6 2 6 0 0 10#29 6 2 4 0 0 10#29 7 2 3 0 0 10#30 7 2 6 0 0 10#17 6 1 6 0 0 10#17 5 1 6 0 0 10#18 5 1 4 0 0 10#12 19 7 1 0 0 10#13 19 7 0 0 0 10#13 18 7 2 0 0 10#12 18 7 0 0 0 10#11 19 7 2 0 0 10#11 21 7 2 0 0 10#11 20 7 2 0 0 10#12 20 7 0 0 0 10#21 9 0 0 0 0 10#22 7 0 0 0 0 10#21 7 0 6 0 0 10#20 8 0 0 0 0 10#22 8 0 6 0 0 10#20 9 0 6 0 0 10#21 8 0 3 0 0 10";
        }
    }


    @Override
    protected void applySpecialParameters(LoadingParameters instance) {
        if (index == 100) {
            instance.fogOfWar = true;
        }
    }


    @Override
    protected void onLevelLoaded() {
        switch (index) {
            case 96:
                setProvinceMoney(13, 9, 120);
                break;
            case 97:
                setProvinceMoney(23, 10, 0);
                setProvinceMoney(21, 12, 0);
                break;
            case 98:
                setProvinceMoney(7, 21, 500);
                break;
        }
    }
}
