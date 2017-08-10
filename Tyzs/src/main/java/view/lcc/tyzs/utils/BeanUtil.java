package view.lcc.tyzs.utils;

import java.util.ArrayList;
import java.util.List;

import view.lcc.tyzs.R;
import view.lcc.tyzs.bean.News;

/**
 * Author:       |梁铖城
 * Email:        |1038127753@qq.com
 * Date:         |05-13 17:27
 * Description:  |
 */
public class BeanUtil {

    public static List<News> getNewsData(){
        List dataList = new ArrayList();

        //痰湿质
        News news1 = new News();
        news1.setTitle("痰湿质相关介绍和调理");
        news1.setDes("痰湿质的基本介绍、发生机理、对人体影响以及调理方案");
        news1.setRid(R.drawable.tanshi);
        news1.setWhat("什么是痰湿？");
        news1.setWhat_content("痰，而是指人体津液的异常积留，是病理性的产物。");
        news1.setJili("痰湿的发生机理？");
        news1.setJili_content("“湿”分为内湿和外湿，外在湿气会侵犯人体而致病；内湿是指消化系统主要指脾的运化功能失常，或阳虚引起的不能气化，在体内的流动失控以致津液停聚。往往由于先天不足，或后天肥甘厚腻的饮食，或体内因寒致病引起。");
        news1.setYingxiang("痰湿对人体的影响？");
        news1.setYingxiang_content("如果脾运化功能失常，必然导致消化代谢物在体内的停滞，而产生代谢紊乱或代谢综合症，如微循环障碍（血瘀）、糖代谢紊乱（消渴、糖尿病）、血脂代谢紊乱（高血脂、肥胖症、中风、心脑血管疾病）、嘌呤代谢紊乱（痛风）；外湿侵犯，阴寒聚湿，易成水湿，易形成痰饮、饮证、水肿、胸痹等症。");
        news1.setTiaoli("痰湿的调理？");
        news1.setTiaoli_content("痰湿之生，若因脾不健运，湿聚成痰者，当健脾化痰。脾主四肢，足居于下，多为湿所侵，以致腰脊膝痛而成痹，脾气得补，则湿自不容留，前症皆除。四神汤，淡能利窍，祛湿则逐水燥脾，厚肠藏，祛邪水，补真水，治痰之本。若因寒聚湿，可根据阳虚质增以牧桂汤以阳化湿。");
        dataList.add(news1);

        //阳虚质
        News news2 = new News();
        news2.setTitle("阳虚质相关介绍和调理");
        news2.setDes("阳虚质发生机理、对人体影响以及调理方案");
        news2.setRid(R.drawable.yangxu);
        news2.setWhat("什么是阳虚？");
        news2.setWhat_content("阳虚是指阳气虚衰的病理现象。");
        news2.setJili("阳虚的发生机理？");
        news2.setJili_content("阳虚的发生既与先天有关，也与后天有关。先天不足，禀赋虚弱；房室不节，肾气亏损；劳倦过度，耗损正气，形气受伤；七情过极，损伤脏腑，久而不复；饮食不节，损伤脾胃，不能化生精微，气血亏虚，内不能和调于五脏，外不能洒陈于六腑，渐至表里俱虚；起居失常，劳逸失度，损神伤形，耗气伤血；外感六淫，迁延失治，表邪入里，损伤脏腑，久则正气耗伤，久而不复；大病之后，失于调养");
        news2.setYingxiang("阳虚对人体的影响？");
        news2.setYingxiang_content(
                "阳气有温暖肢体、脏腑的作用，如果阳虚则机体功能减退，容易出现虚寒的征象。常见的有胃阳虚、脾阳虚、肾阳虚等。" +
                        "阳虚日久，则会导致阴寒内胜、卫外不固、气化无力，易患感冒、咳喘、泄泻、肿胀、阳痿、遗尿、郁证、痛经等疾病，" +
                        "免疫功能的紊乱，免疫监视功能降低，炎性细胞因子相关基因表达上调。");
        news2.setTiaoli("阳虚的调理？");
        news2.setTiaoli_content("阳虚的一般证候畏寒怕冷，四肢不温；完谷不化；精神不振；舌淡而胖，或有齿痕；脉象沉细外，" +
                "特定脏器兼证可供辨别。牧桂汤，能引导阳气，调和营卫之气，助气上行阳道。能导引阳气宣通血脉，使气血同行。散寒邪而利气，" +
                "下达暖丹田，壮元阳，补相火。为诸药之使引，因其香窜之气，内而脏腑筋骨，外而经络腠理，" +
                "倏忽之间莫不周遍，故诸药莫不透达之处，有此引之，莫不透达，一切虚寒致病并宜治之。特别适于阳虚质");
        dataList.add(news2);

        //阴虚质
        News news3 = new News();
        news3.setTitle("阴虚质相关介绍和调理");
        news3.setDes("阴虚质发生机理、对人体影响以及调理方案");
        news3.setRid(R.drawable.yinxu);
        news3.setWhat("什么是阴虚？");
        news3.setWhat_content("阴液主要指人体内的津液、精液，阴虚是指由于阴液不足，不能滋润，不能制阳引起的一系列病理变化及证候");
        news3.setJili("阴虚的发生机理？");
        news3.setJili_content("阴虚成因多由先天不足、久病失血、或热病之后、或杂病日久伤耗阴液，或因五志过极、房事不节、纵欲耗精，过服温燥之品等使阴液暗耗而成阴液亏少，机体失去濡润滋养物质所致。");
        news3.setYingxiang("阴虚对人体的影响？");
        news3.setYingxiang_content(
                "由于阴液亏少，机体失去濡润滋养物质，而阴不制阳，则阳热之气相对偏旺而生内热，故表现为一派虚热干燥不润、虚火躁扰不宁的证候。若阴虚日久，则会导致易生皱纹，易患久咳、不寐、噎膈、胸痹、便秘、" +
                        "盗汗、内伤发热等症，易患肺痨、高血压、中风、消渴（糖尿病）、高脂血症、哮喘、肺结核、冠心病、脑血管疾病、风湿类风湿等疾病。");
        news3.setTiaoli("阴虚的调理？");
        news3.setTiaoli_content(
                "阴虚的人应该多吃一些生津添精的食物，以滋阴潜阳为法。仙人余粮，入脾、肺、肾三经，为补中宫之胜品，" +
                        "能补肾填精，增加体内的精液和津液，宽中益气，使五脏调和，肌肉充盛，骨骼坚强，是补阴之胜品");
        dataList.add(news3);

        //气郁质
        News news4 = new News();
        news4.setTitle("气郁质相关介绍和调理");
        news4.setDes("气郁质发生机理、对人体影响以及调理方案");
        news4.setRid(R.drawable.qiyu);
        news4.setWhat("什么是气郁？");
        news4.setWhat_content("气郁是性格内向不稳定、忧郁脆弱、敏感多疑等情志不畅的病理状态。");
        news4.setJili("气郁的发生机理？");
        news4.setJili_content("心主志，主宰一个人的精神情志活动，肝主谋虑，辅佐心神参与调节思维、情绪等神经精神活动的作用。若气郁的人精神情志调节任务将加重，则更加加重心、肝负担与消耗，心、肝不得调补，从而使得心主神志和血脉、肝主疏泄和生血的能力进一步下降，使得气郁状况进一步加重，循环往复每况愈下。");
        news4.setYingxiang("气郁对人体的影响？");
        news4.setYingxiang_content(
                "若气郁日久，则会导致肝气郁结，横逆犯所致胃脘痛；气逆于上所致的头痛、头目昏眩、面色晦滞、目赤、眼干、口苦口酸、吞之不下、咯之不出、恶心喜呕，吐血；暴怒伤肝，气乱血动，血液妄行，致成崩漏；经前乳房胀痛，郁结不孕；心不得主志，肝不得主谋虑，情志所伤，脏躁失养，心神惑乱，" +
                        "精神抑郁，健忘，失眠，多梦，惊恐，易发郁证、脏躁、百合病、梅核气、胁痛等疾病");
        news4.setTiaoli("气郁的调理？");
        news4.setTiaoli_content(
                "根据气郁的发生机理，要打破气郁加重机制，要提高心、肝的自我调节功能，" +
                        "而这些功能的实现都靠心和肝的营卫充足方能正常。心、肝皆以血为养，故用益心血补血疏肝是调理气郁的根本" +
                        "。三枣羹，用益心血，籍香以透心气，得温以助心神，凡志苦伤血，用智损神，致心虚不足，精神失守，惊悸怔仲，" +
                        "恍惚多忘，虚汗烦渴，所当必用。又取香温以肝胆，若胆虚血少，心烦不寐，" +
                        "用此肝胆血足，则五脏安和，睡卧得宁，特别适于气郁质。");
        dataList.add(news4);

        //湿热
        News news5 = new News();
        news5.setTitle("湿热质相关介绍和调理");
        news5.setDes("湿热质发生机理、对人体影响以及调理方案");
        news5.setRid(R.drawable.shire);
        news5.setWhat("什么是湿热？");
        news5.setWhat_content("湿热是热与湿同时侵犯人体，同时存在体内，湿热内蕴的病理变化。");
        news5.setJili("湿热的发生机理？");
        news5.setJili_content("湿与热合并入侵人体，或因湿久留不除而化热。不同的病因在不同的个体会引起不同的湿热证，如郁而化热，导致湿热郁阻肝胆，形成肝胆湿热证；如过食肥甘酒酪，酿湿生热，湿热内蕴中焦，形成脾胃湿热证；若湿热侵袭大肠，胶结不解，壅阻气机，则成大肠证；若湿热之邪，下注膀胱，" +
                "膀胱气化不利，小便异常，则成膀胱湿热证；若湿热流注关节，关节局部红肿热痛，则形成湿热痹证；" +
                "若湿热之邪流注淋巴经络，则形成淋巴结肿大结节。");
        news5.setYingxiang("湿热对人体的影响？");
        news5.setYingxiang_content(
                "若肝胆热证，则易发黄疸、牙痛、牙龈发炎、胆囊炎、偏头疼、胸痹、胆及胆囊结石及肝胆疾病；若脾胃形成湿热则易发复发性口腔溃疡；若湿热侵犯大肠，则大便黏腻解不尽；若湿热侵犯尿路及膀胱，则易发前列腺炎、膀胱炎、尿毒症、尿路结石等泌尿系统疾病；若湿困热不易散发，则容易患湿疹、" +
                        "青春痘、脚气、股癣、疮疖；若湿热伤肾，则易发脂溢性脱发、痿证；若湿热流注淋巴，" +
                        "则易侵犯免疫系统，易患淋巴系统疾病。");
        news5.setTiaoli("湿热的调理？");
        news5.setTiaoli_content(
                "湿热的调理，主要围绕“湿”和“热”来调，主要进行利湿清热，利湿则需健脾，清热需要利达，故选用薏苓汤。" +
                        "若湿热引发炎症，则应辅以杀菌的消炎药。薏苓汤具有利湿清热之功，利湿而不泄气，利湿而不伤阴，" +
                        "能益气生津，清热而不伤脾胃，特别适于湿热质。");
        dataList.add(news5);

        //气虚
        News news6 = new News();
        news6.setTitle("气虚质相关介绍和调理");
        news6.setDes("气虚质发生机理、对人体影响以及调理方案");
        news6.setRid(R.drawable.qixu);
        news6.setWhat("什么是气虚？");
        news6.setWhat_content("所谓气，是人体最基本的物质，由肾中的精气、脾胃吸收运化水谷之气和肺吸入的清气共同结合而成。气虚，是指由于元气不足引起的一系列病理变化及证候，以气息低弱，机体、脏腑功能状态低下为主要特征。");
        news6.setJili("气虚的发生机理？");
        news6.setJili_content("气虚多由先天禀赋不足，或后天失养，或劳伤过度而耗损，或久病不复，或肺脾肾等脏腑功能减退，气的生化不足等所致。");
        news6.setYingxiang("气虚对人体的影响？");
        news6.setYingxiang_content(
                        "气虚病证可涉及全身各个方面，平素体质虚弱，周身倦怠乏力，精神萎顿，脉象虚弱无力或微细，水邪泛滥而成水肿，卫表不固易患感冒；病后抗病能力弱，易迁延不愈；脏腑功能衰弱低下，易患内脏下垂，虚劳疾病（腰椎病、颈椎病、颈肩关节病）。\n");
        news6.setTiaoli("气虚的调理？");
        news6.setTiaoli_content(
                "气虚多由阴虚、阳虚、消化不良食谷不化等多种原因导致，该体质为结果性体质。因此，气虚要在辨识其他体质的基础上配伍调理。虫草“秘精益气，专补命门”，在调理气虚体质时可辅用。\n");
        dataList.add(news6);


        //血瘀质
        News news7 = new News();
        news7.setTitle("血瘀质相关介绍和调理");
        news7.setDes("血瘀质发生机理、对人体影响以及调理方案");
        news7.setRid(R.drawable.xueyu);
        news7.setWhat("什么是血瘀？");
        news7.setWhat_content("血淤即血液运行不畅，有瘀血。血淤证可见于很多种疾病。一般而论， 凡离开经脉之血不能及时消散和瘀滞于某一处，或血流不畅，运行受阻，郁积于经脉或器官之内呈凝滞状态，都叫血瘀。");
        news7.setJili("血瘀的发生机理？");
        news7.setJili_content("“血之在身，随气而行，常无停积”。血之运行，听命于气，故曰“气为血之帅”。因此，气分受病，气机不畅，或气虚推动无力，是导致血瘀的重要机制，故有气滞血瘀、气虚血瘀的说法。此外，邪气直犯经脉，影响血的循行，也是导致血瘀的常见致病因素。“寒邪客于经络之中，则血泣，血泣则不通”，故阳虚易可致血沉则瘀。若津少精亏，血虚则结，亦不易血行则瘀，故阴虚也可致瘀。瘀滞形成，虚实夹杂，虚瘀相兼，病机错综复杂，给辨证及治疗带来了一定的难度。");
        news7.setYingxiang("血瘀对人体的影响？");
        news7.setYingxiang_content(
                "瘀滞形成，则阻碍机体气血生新不顺，则机体虚弱更甚。瘀血阻塞络脉，气血运行受阻，以致血涌络破而见出血，故易患出血，由于瘀血停聚体内不除，堵塞脉络，或为再次出血的原因；瘀血内阻，气血运行不畅，肌肤失养，因此面色黧黑，皮肤粗糙如鳞甲，甚至口唇爪甲紫暗。女性多见痛经、闭经、或血中多凝块、或经色紫黑、崩漏。瘀血的部位不同，临床表现也不一样。若心脑血管瘀滞，易发中风等。\n");
        news7.setTiaoli("血瘀的调理？");
        news7.setTiaoli_content(
                "“气为血之帅”，气病则血瘀，影响气机的有阴有阳有痰。因此，在明确辩证体质的基础上，进行阴阳调和、利湿清瘀是调理血瘀之本，活血化瘀是调理之标。因此，在体质辩证的基础上调和阴阳综合调理体质，同时辅以纳豆菌粉化瘀为上上之策。\n”，在调理气虚体质时可辅用。\n");
        dataList.add(news7);


        //特禀质
        News news8 = new News();
        news8.setTitle("特禀质相关介绍和调理");
        news8.setDes("特禀质发生机理、对人体影响以及调理方案");
        news8.setRid(R.drawable.guomin);
        news8.setWhat("什么是特禀？");
        news8.setWhat_content("特禀质，是由于先天禀赋不足和禀赋遗传等因素造成的一种特殊体质。包括先天性、遗传性的生理缺陷与疾病，过敏反应等。");
        news8.setJili("特禀的发生机理？");
        news8.setJili_content("特禀质的成因有先天禀赋不足、遗传等，或环境因素、药邪、医过因素等。特禀质，这是一类体质特殊的人群。其中，过敏体质的人易对药物、食物、气味、花粉、季节过敏。特禀体质的人会出现打喷嚏、流清涕，就是因为卫气虚不能抵御外邪所致。\n");
        news8.setYingxiang("特禀对人体的影响？");
        news8.setYingxiang_content(
                "特禀质与后天阴阳偏颇成因关系不大，在辨识调理时非常苦难，容易出现很多疑难杂症。根据不同的发生途径，对身体的影响有以下几种：\n" +
                        "1）遗传性疾病：出血性疾病（血友病）、癫狂痫（精神分裂症、癫痫）、消渴（糖尿病）、多指（趾）症、眩晕和中风（高血压）、多囊肾、色盲、近视，以及过敏性疾病等；\n" +
                        "2）先天性疾病：如先天性心脏病、原发性闭经等；\n" +
                        "3）生长发育障碍：如皮肤脆薄、毛发稀疏发黄、五迟（立迟、行迟、发迟、齿迟、语迟），五软（头项软、手软、足软、肉软、口软），解颅等。；\n" +
                        "4）过敏性疾病，如哮喘、风疹等。\n");
        news8.setTiaoli("特禀的调理？");
        news8.setTiaoli_content(
                        "特病质若先天脏器受损或不足，则不易调理。若卫外不固，可在辨识体质的基础上进行体质的综合调理，若有免疫缺陷或药物致敏可辅以增强免疫能力。食用菌是国际公认的抗癌和长寿食品，是国际公认的免疫增强剂和激活剂，因此对于调理特禀质具有非常特殊的优势。\n" +
                        "因此，综合调理所存在的各种体质并辅以食用菌或灵芝等产品进行调理为上策。");
        dataList.add(news8);

        return dataList;

    }
}
