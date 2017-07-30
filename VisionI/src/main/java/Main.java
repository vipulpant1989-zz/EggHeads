import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.List;

/**
 * Created by vipul.pant on 7/18/17.
 */
public class Main {

    private static final String base64String = "data:image/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcU" +
            "FhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgo" +
            "KCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCAHgAoADASIA" +
            "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA" +
            "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3" +
            "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm" +
            "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA" +
            "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx" +
            "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK" +
            "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3" +
            "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDzctSb" +
            "qARSVjYq9xdxpAaaW4PrQM45osIdn3ozTAfeikMdnrRmm5ooAcTSbuKZzml5/CmkK1wyOeelJuzn" +
            "FLtpAvFFg2F3eoppNOCGjYadg3Ggnpk0nPNSCPjrSiP3oArt061GRVox8c0wxDHT9aBFBuCeahcc" +
            "nJrRMAwaj8oc4H60rD6WMwjg8U0qeeprU8le45pPKX2qkIyADk4B/Kk8s5OAa2PLHYU3yhQhGR5T" +
            "9NtNEL9gc1s+X9KBGOfWgZj/AGd8cA0gtnya2fLGKTYKVxGQLZqPsrEc1r+X60bB+FNO4GQLVgDy" +
            "MUotjg89PatQR9j0pREOlCAxzaHnrUf2cjPX8q2/Kx6UxoQQeKaYGMsZJwQSTxVhbUFDnOauragN" +
            "nFWBFxgYpDMQqVPIpY0DGr9zAMEgfWqiqVPApp6AW4IFx71Y8sbe9RWz5XBPNWgOKLCKwhAzjrUM" +
            "yBFOMc1fYDBNUT+9lI5wKEBHbwnrjmp3iJVhViOPA6U8LxzSsBlfZG74wasCH93t61c2e9KqDFCA" +
            "zrcbHKnseK1bc5HNVZotp3CpoDkU0rgW2jDJ7iqjQHnrVuNsggmneXwTkY+tIDNMfPT+dRTx4RvY" +
            "VfK8njiobhMxkUwMeKLMo9Mjn8a14kIX2qC2tyZCe1aCx8YqmgIQvWjb9am2n8aAp5qbAQBeCAeK" +
            "AnXNTBetKFOKLAQ7RTStT7KTaeadhkQXrTdnXNThaNtKwiuEIpwXrz+lTqnt0oK0/ICDZ3pQnoam" +
            "2cUoTj/69ICvtHrzRs7A8VPsPpSbKdhkQXn1o2detTBe3FG080rCIdh9acq471JtNAXiiwERUnvS" +
            "qvHtUuzmjZzTt0AjC8cUm0VNsPehU9aVgIguBQFPNTbKPL60AQbOtOAyDUwj45xRs9BQBaAFIRT8" +
            "YzxQRipaKWpCR146Uq4NPxSBfegLhijHWnCgUWC4nFHFKBS49qSQ0xmKXAp+Pam4osCdhAMCgAUu" +
            "DijBp2uFwFJinBTQBiixNxAM04LQExT1XPSmkO5Hs4600r2NWAhNI0eAQafKIqletMK9asFSM8Uw" +
            "r15oEV8U0rVjaKaVoAgC/j+FG2pgvHtSbTQBFt5pu3rU2096AnpQBCFwOlGKkweRijZTSAjA9qNt" +
            "SBf0pQvYUWCxDspQtSbDzx+lAU0WCxFtFLt+tS7OKAmQeKLBYhC0u3jpU3lmgR+1FgsVygwQapXE" +
            "G3lenetbysj/AOtTGhyCD/KloBkQqV9auxtkds0rWxUk/wBKYynHWqS0AZcNg7RToYgoPrUcS5ky" +
            "1Xo42xwCRRsCdxqrwaUKSeATU6QP02n8qsW8BDZKnFLToMpLET1zUjQlc5rV+xg/dxSy2o2ccmlc" +
            "RhNFkHio4l2EjFaws256VBcWjIpcVSeoyCMccVIAcYNS2sHmJkMOatLZkjO8flQBn7Dz1qN17c1r" +
            "iyGDlqZaWP2i5Kj7ueT7UIWpUs7Q7CxB+mKlaLGeK6+00TdCAgJzx9aozaYik7gcU7KxKe5zZj9v" +
            "0puz2rfNhEAetRmzi6YNJFIw/LpPLFbv2WPBG2m/ZEwcL+lCBGII+aPLzW4tquCNgo+yjHCDj2pX" +
            "6AYZiPrQI+OhrdFqMY2Uotf9ihMDBER54NL5J56/lW+LU8/KPypwtTzlR+VFwuc8LdsdDSi3bH3T" +
            "XQran0H5UotDzkD8qVwOe+zPj7ppRbP/AHTXQi1PTj24pRZnvii4HOi1c84NO+ySd1IrofshxSi0" +
            "IH9KAOdFm+elPFk/pXQfY85605bPjrn8KL6Ac79ifGMDFOSxfnOK6D7GKctmOnJoTA54WLY6gU5b" +
            "E45YV0Is156/lR9kUdv0ouBz4sM5+alFiB3NdALMc4H6UC0X0oAwRYrzyaUWSc8mt77Mo6D9KUW6" +
            "+g/KgDmNtJt4NS7KAhpAQhMZ5pQuOc1MEOOhxS+UTzg/lRYdyvtpQvBFWBAfc04QHspoSEVQgpwH" +
            "GKsi3b+6aX7M2OhpgVNv1o21cFq3enC0buaQXKIXIpNvFX1s+uWH5U4WeP4v0pgUMY70AHtWgLRe" +
            "hNSJZp7/AFoQGaq84qRI600tEBzzViK1i6FTn601YZmRQ57U+W2O0nHFdDZ2ETt93FdAvhySSAut" +
            "uzDbu6dvXNUmiW2eYvEQTULRnJ4rrbuzQE4jFVWtuOIx+VK9ijmvKbBIHFHksc4Brovsh5AApotm" +
            "wcAVIjnxAxGcH8qX7PJjhTiugW0Y9TTvsR9f0pAc79lkP8JpRaSeh/OuiFiT3P5U8WIwck8fSmBz" +
            "YsnI5xmnCxcjqK6NLJeepp32JPQ5+tFwObFi/cil+wnuwrpRZIO1KtnHz8oocmBzYsv9qnLYrjkm" +
            "ujW0T+6KkW0Tn5R+VF2BzQsU9/zpRYoOx4966YWyj+EUot0/uigDm1skwf3ZpVs0wcRmuj8hehFK" +
            "LdR0ApAc6ll6RfpT1s25/dj8q6ERj0GfpQYwM09wOeNkxBBjFZd7pEihmjXj0rtfLXHao2iBB6UA" +
            "ebtDsJypB9KkgmaM+o7e1dfqOkxXAJUbX9QK5y702e3J3KSPUU0+4rFqydZgcMA3oa0YrQnJDCub" +
            "TK8gn/CtG01CaEYBDD/apuPYV7bm0tq3TP6U77Ie5NVoNXQjEiEGrkepW7DlgKXKx8yIxYj1NJJY" +
            "qwIPpU4vYD0kU/jQL2HBwwJos2F0YdvAIL4wScKfu1r/AGZApJ6VR1NhLskiHzIcg+1WYRJcRqGJ" +
            "UHGe2aLOwAIlmJSLtjJ61p2FmkRUAD3pLWJYlwoGBV2AjIppDPSvCmnWsul2kpjBl8iQ59SCRk15" +
            "5dQA/lXa+HbqSOwiVGxgOv51yFyQHYVTjZXRnCTZlm3HYCm/Zx6CrTHk5phxzWdjQreR7Ck8kc8V" +
            "OT15pM5osBD5IoEQ9KkHWloAiEI9AaeIgPSnjijNIBBGMHNHlrTgaTPvTATYPSjYOcUuaAaEgAIO" +
            "aXYMUZozQAgQDtRtHpSZOMUoaiwChRngUuBTQaXNAC4FKMU0Yx1oz70AKB1o6Umabu96AH0gqPd+" +
            "NG6iwD6Sm7vSkzQBni1/2BR9lI7CtPYO9GwYx2pAZ62xweBSi1PtWgFFKBxilqBni2PSnfZvXmrw" +
            "GKXFGoFEWnFKLQDOQaurxS5FCQFMWq46GnC1XHQYq2MdqM1QFQWq9gPypwtl7gflVkHB60maQEIt" +
            "wOw/KnCBccgflUoPrRkdqYCJCPapY4gOw+uKarGpYu9NIC5aIOfp2r1Yzww6DKu9dy2uAM/7NeXW" +
            "QyeB14xXb6k2zTJ/+ueP6VcYkSZwN1Gueg4xVJ0GelXbpvmPoKoOxyalrccXoN2rgim7R7UFqZu9" +
            "amxRIAMUAAUwE9jRn1oAkHtRximBqNxoAkFKPcCow2KM0APP4flQKj3GjcfWiwEmfSgHrmo80A9a" +
            "oZLupd1RUD2pWFYkz9aMjtUfrRzRYLDsmjd9KaM4o5xTGOz703I5pKTtQA1uhqFkDA5AqcjjmmlT" +
            "RZAZdxpsEmTtAJ7jiqEmkMM+W4P1GK6Db1pu3rUrTZiOZNhOufkz7g01YJl4MbflXTGMUmzHSqUm" +
            "hcpzyQTZ4jarMNpM3XC+tbHl0oXHWnzMOUpxWyry2WNXI1xSqopy1NurHYclTxdfxqBaniHP5VQW" +
            "O18OkvZpjk5Irm73HmSAev8AU11ngaMSS2iOMhpWBHrxXM6onl3My+jsPyYirfYyjHexlueeKj7m" +
            "pXHWojxUGqGHvTcmlPSkxwaQwzRRg0mDSsxDlPBzSjPc00AinetKwCj2ooxwQKMe1AAO+aM0lHbm" +
            "iwDh3pO1GKTBx1oABnFFLRTuFxpFAz36Uoox70xi9jikGe9KKUDNSITk0mPzpaTnGKLAJg80lHPa" +
            "j60WASiiigCXdwaFPvTPrQuc0APzQM0gzz6UUALmlB4pB9KM0AGetGaAc0n50eoDs0A5702ihAGf" +
            "elzTaTpTsFh4PrRmkpVGe9CQD04qePk8VCvNTwj0qlqJs19Kj3zRqM8so/M12niG0aDSLiQoduQu" +
            "fxxXN+GIDJqNoDjmVP513fjNceG5QP4pV/8AQiau1iOjPJbo5LexrPc9a0LsYdvrVB/61L0HHYhY" +
            "9Rnmm9fWntjmkUHtUpFoUUv40AUY9aVhiYpe1GKcBRYSQlIBmnAUoFNIYzFLinbRSgccCgBgBpQK" +
            "eF9qNtACcUgFOCmlC+tCQDKKkC0baLAMAzSVLj0oxkUWAjxRin7aNtMCPFJipNtJigCPaKbsqXFG" +
            "KAIdn50m3FS4FJjrQBFtpMe1TAcUhUUARYpQKkAGKAOtADFWpYh835UgqSIc9KaRLZ3ngD/j5s/a" +
            "fFYGvRldRuR2EjD/AMeNdD4BBE1vt5InFZfiqEpqt2D94SsfzJrXlMk9zmZB1qA1akU8/Wq7DrWL" +
            "RqncjxSU7BowaQ7jcUgU08A0u2mCGAe9LinhfSlCnmkAzFGOKfsxS7TjpTAipRT9uB0oC596VgI9" +
            "tG01IEzQUoHcjx1pAtS7aAh7Zo5SbjMUgFSqp6YP5UbDzxRYaIsUCpfL69aAvbB/KiwyPFJtqXyz" +
            "S+WfShISIMUhGcipxGeRigxnFFhlcAY6UmB2qx5Zxg0giNArkGKULTgOuKXFSAgHWlx155oAzSgU" +
            "7BYTmkxTwKTbQgQg70v40oXilC+lFhjKBTwv1o29aaQEeOKTHepMGjBoFcZjmnKMUoBpQtMLixjm" +
            "rtquTiqyJzWjZIS3Q1pBXM5bHVeC4N+r2nHG/P5A/wCFdd45XGhIvrIp/LJrG8FWfm36ZLAKpJI/" +
            "L+ta/ji1EemxMpc5fHJz2NaSSvYzTdmeT3g+dhWc45rUvkIdqzpByaymtzSOxXI5oA4p5Q80qxmo" +
            "sXcYop2OKcI6eI+OlOwXIqBUwj6804R+9KzC5EF4zzSBeanEdOWLIp2ByK+wUtWRCKXy1o5WCkis" +
            "ooxmrSxD0p6xe1NRYcyKYWl2/WrghA6CjyhRysFJFIL9acFPYfrVwRADoKPLA9KagxcxSCHNLtPp" +
            "irwjFJ5YoUAUijsPoaAhq7spAgxQo6C5in5belJ5bVdCUbaOUfMUfLPpQI6u7PakC01Edyl5Z7Zo" +
            "ERq6Eo2e9LkJ5il5J9aQRGr232phXFPlEpXKohPPtQIferQXigJS5B3Koh65p8Sc8VPt60Rrz9cU" +
            "0rMV9Du/h0ga4jB7OD+QqLx3bAazckDhiG/SrPw54ucdw4x+Rqbx3H/xNJc9wP5Vq9/kQmrM4GWA" +
            "ZNVWiHNakq9aqsuc1DWpSZTMQ96BD+NWdtJtNTyjTIBEDR5QqyF60baTiNMg8oUojGOlTbaUL7UW" +
            "sguyARj0o8sVY2Uu0UrILlcRik8sZ6VY20BetFkDZB5dJ5Y9qsBeDSYosF2Q7B7UioAc1PtpAtAr" +
            "kW30pdgNSbRmnBeKGh3ISo5puypylIFoBMh2UbcVMRQFH1pWGmQ7aDGMetS7eKMHtTS0AgKCgL6C" +
            "pccc0mKTQ0UNh9qAnsKnA9aMVI0RBacEqUJinBeOKYEAjP4Uvl+36VNTgtFguVxH7U4JxU4TilCA" +
            "dqaQmyDy+PpTCmKtge1IUzmiwrlMr7UBB7CpyvWmbRQkF77DAlOVRnFOUU5RTSFcdCnNamnLls1Q" +
            "hX8q19MT5v8APrW0FqRLY9D8CxbZJm64QfqaveNhu0tR/wBNP6GmeDI8QTvjrtH86k8Y/wDHjGP9" +
            "omiWtS3awL+GzyPUVAkbp1rLdRzW1qSjzGNZMgxmiotWEHoViKei0u2noKySLuIFp4FOUe1P21SQ" +
            "r2I8ClA4qRV65pQKdhXIwtKq1IFz0pVXrTAaEpQvWlpwGaA2GhcU4Kaeq04CgBgUUbakAFBHFAX0" +
            "I8Yo25qQDPWihCGbaTZx0qQUuODQMh2+9G2pNvFJtoAi2mjbUu00hHagdyLFAHrTyuBSAeooBDNv" +
            "FAX3qTbxQF9aBXIttNK9cVNj0ppHFAEW2gZzUgHBpMUAhu3g0iD5hj1/rUuOKaoANAjuPh7xdNnu" +
            "y/yNX/Hi/wCnn/dWs7wAcXZA/vJ/Mitbx4v+mH/dFaSWqISsn8jgph1qm64Jq/cA5b3NVZF4NQy0" +
            "yDHUUuOKWjNIBAOKMU4CjFIBm2lAxTwvFOCnBpWGRClxT8U4CiwXIgOaKk280YosFyPBoxUmKTHW" +
            "gZHijHNSYoC8UkCI8UoFP2805R7UwIsGkAHOKnxSAdagVyHb7UYqQjijbxQWRbeDRipNuKaQaCbD" +
            "cU3bUmKTBoKKWKVRxSgetOA4qRoYBTgKUClA9KABRUir60iKc1MBimhDAtG0Yp9Lg0wItppCtSgZ" +
            "pMUCZXZcZqPbjNW2Tjg1Ey4oBMhApyinheOacq1UUJj4V56d63NLQZHFZEC88jvW/pMeWA/z1raC" +
            "1M5PQ9G8JptsHPqw5qLxgf8ARYx9aueHF2aavuSf1qh4w5jjHoDS/wCXw9qX9dzy/UhmRqynXrWz" +
            "fj94fasp1zmiotRQXUrbeakQcUY5qVF4NZpGj1EC04DjmlxTlXjkVRIwD8acF9BTwo9BTgvpxQBG" +
            "i+opwX0p6ilxzQhjNtKB1p1AGOlAhAPWjFLTlXrmgewAHFKFxTlFOAppAQ7cUY61NikxSsFxij1p" +
            "dvpSqOKX6U0hEZGM5pMfnUhGaaFOKAGYpMU/19qT1osMZijHpT8U3vQkCEwaQdOlPxmjApB6jMCm" +
            "EdqkAoIoDYjC9c0baeO9JQIZjg4poHNSDvSKOeaBnYeBDtuyT3Kfzrc8cg/aRx/yz/qawPBDbbvr" +
            "nlPw+aui8dD99Hzxs/qa1e8SEtH8jgZhVNx1FXpx1+tU3HJ96zZUSHHWgCpMUgHFIBo9KAKeBRig" +
            "YwKTShaeBilFIBoUc0BaUUuKAExSdKdikx60AhKTBp2KAKQxoHFAGKfjikFACYoA9KXFKopWASkx" +
            "T2HpTaTC4mKB0opRSBDcDmkI606g0Bch29etIQe1S9M0xqBoqUoBNP246UqipuVsIF4oC9cU8Cgd" +
            "KSBiJUg9qQDrT1FUhNDQOKd2pQOKB0pgAHFAHWndjSDgUIVrkZXqaaR19alpAPzpoLEO2nIOKeFz" +
            "T1QVSYvUkhTJ/Kuh0eP5h9B/Osa3Tniuj0WP5gf89a1huZy2PQtHXZYRD1H9TWP4u+6noF/rW9Zr" +
            "ttYxjoorD8WDKD2X+tRB/vb+Y5r92eaagvzsBWa461q34w59azZBwaqerYRWhVC81Mo9aao5qVF9" +
            "ahFgFpwGKUU9F4JpkjAuaUKenNTqvpS7aLjuQAdc0BalxTlTNCAi28U3aRVny6aU4oBOxCF604DF" +
            "Ox6UAGgBAKcuOaAMVIoHPFO4DMe1BFSEZpmD60gGgcUmOKfjikxTuA3FGKUik7UrhYb60hHGBTsU" +
            "mKYWGY460mKk28Gk20BcjoxUm2lC0hkePemkZzVgLwelIVpisV9p5pvSpyvWoyuc0gGUi9SKkA4N" +
            "NA5oA6jwX/x9nJ7r/wChV0/jlcvFz/Bj9a5Xwedt0c8YCn/x6us8bD54j/sf1NaPeJC2d/I4GfOT" +
            "VN++KuzdDVRqhlIh+tApSMZoApDGgdaKcO9LigBMUDninAUgGc5pbAkIF/Gn44pVHHWlxwaEAwA8" +
            "0uDThRTEMxSAdadigDFIYnakAp2KQe1IYAUoHYUo6Uo6UAIRxUZBzjFS0mKTER4pAPepMcU01JY3" +
            "FJS0lACGmHvTznmmkcGggr4pV6mlpB3qTSw4CnKvHWkBpy8UgFA44oApQDilUcGmIAPU0uKKXimg" +
            "AUAdaBz3op3ATbSYAp3ak5pgCjrUiDJNMUYzUkVOKEy5arzXTaImWUY6/wCNc7aDnI9q6rQU+dB7" +
            "j+da0zKXY7qEbYlHsKwfFP3P+A10I6Guc8Vng/7orOi7zKqL3fuPPL777VmOPvVq3g+dsVmyDk1c" +
            "nuEdiBF5NSIpoRcmp41qIldBipT1U09EFPCgdBTuNIaqkZp2Mj1p2MUoFK4rDNlOVTSgEUuKEyht" +
            "Rkc1KQcGk25polIhwaFHWpgnFG3FO4IjUU4cUuDSquaTY7CUVIE9qQr7UXCxHtptSAUhHWmhWIiK" +
            "b2qTFNxTAZRinbfWkwRQrB6iGgClApRmgEhQnFOC8U4dPWlHSpCw3A6U1gMYp9MbPNCKGY61Ey46" +
            "VMDTGHWmTaxFTcc040lArHQeFP8Aj6Y/7IH612HjQZ8n/cNcb4YJF0R22/1Fdr4wG6OA+in+daX+" +
            "FkJbnATDqfU1SbqavzDlqpuBk1DKRAQM0AYpxHNIKSKQlGKdijFAhFpwoApcULzAMYopcUoHFADc" +
            "UmKKKQWEC+1LS0cUkA0DjikFPxwaQCiwIQClFFAoGL2pO1OxTfWgEIOlJjvTgOKMVAr3GEccCmVL" +
            "imstAXsR0dAaXFHQGgpdysKUD2zTwvvSj2qTQRVpQKBS0CQoHFKKBSilYLCAUAetOHSlGPSmFhMc" +
            "UmKkxkUgHXOKEJoZijFOx1pD9apaiEFSximoKljHNVETLtovP4iuw8OJmRPqP51ylkuSO/Su08NR" +
            "/vk9M/41pHuZtHV4rmfFRBcj2FdN2rl/FB/eOfp+lRQ+L5Dq/DbzOFuuWNZ7rnNaN11OKoycE05M" +
            "cUMiHXFTgAVHCOtTKD0qEWApwXjnrQAMU4UAJgUoxzijFA4zTAUUoxikHtTh05oQBigKKKUUWAXA" +
            "pNvFOHINKBSAi205RxT9vvRtNADMUhA5qQDFNPeqAjK9cUwrjNSn6031FJAQkUmOKmIpuOOKdxWI" +
            "gKXbTsDmkwaaYWE28Uij+dLRii4wFA96B6UUALio2qSo24zSQrDR0NIe9HrTT3p3GNNR+tPxwaZ3" +
            "NBPKbnhlv9JPqVIruPF/+ogPqCK4bwzn7Z1H3TXd+LBmzts+h/lWnYlLc4KYcmqbr14q7OOvtVVh" +
            "1qGBW5yaBT8daTFA2IFo209RSfhRcEIBTgvHFCinAYpXAj2mlAwKcaTtQA0kc0U7FJtoAZSgU4Lw" +
            "elGPU0ANA60AU4CjGKQDQKUCj60o9qB2ExyaQ1J3qMjrSBbDcUAHvS0VIWDHFJil7UlAhhH5Uw8Z" +
            "p7DjApvqCaCyIDBpR70tLUIsMUAUuKUD1pgJilA4pcd6VVyPagBAPSnAetKBxilHegBBSil/CkoA" +
            "bRtpccU4VSENUY4qeIVGBU0S+tNMlo0bEDd+Irt/DKgOCfSuLsBk/liu78NoAhx2FaJ+6zN72Nzo" +
            "K5PxOcyuB7/1/wAK63Fch4lP71wPrSofF8h1dTjrgZZsVRkGc1enJyapHvmpkVFdBYehqYLxUMfF" +
            "WU5FSmVYaBRin4oFO4rjcGkxT6MCgqw0dKUZpaUUXFYTFKBS4oFAaCgYFKO9A6UUBYWkoJpu6i4W" +
            "HAjHJphPXFGT60DnrRcLDcZpMU/jHFJ60XCwzFLSetO5x7Uh2IyBzSYqTFMK9apMViPHWinUlCYW" +
            "G4FLxR+FA6dKOYdgqJz1qQ9Dioj3p7isNyeaaeppfWmtSTFYbnINRkjNOJ4OKgZsMeelHQDe8ONt" +
            "vB6FDXoHic/8S61PqD/KvNNCkxeL7hh+hr0XxE+7SLE9mX+larVJ/wBdSNm0cXOOT9apydatzHJN" +
            "VZKlgiEGjAPanGkA9aQ7AMDpRkUuKTFK4rCYHbilUUoX1oC0XHYKMUopaVwSGc80YpxGKShsdhAO" +
            "tG31paKVxBjFHFGKQCkKwmBQAOaXFKKdwsNxSFeKfTe5ouMjpKfjrSYxSENoFBooHuNPemHipKbj" +
            "rQURAetOxSgU8CoRYzFOQcUvalXvTATFKo4pcUUAGKB0ooFABQPaloHehANop+KUUxCKM1Yg61Gn" +
            "SpoR1NUhWNPTxzge1d34dGIW+gFcPpw5A+ld5oIxAxFXtFmdtTV/hrjfEhzK/wBa7I/drifERPny" +
            "c8UUOr8gqnLT9T+tU3XrVybqaqHkmpluXEWME/hUy9Kjj6GpRz9ahMqwo96DSCl9aLgA70UUo5ou" +
            "AgGacAaUACjFO4CYpRxSetJmlcB1GeD6U3dSDOeTRcBSaaKdSUAIB609QDSClU8UJgKBxTSvpTgf" +
            "SlpgiEiinkcdKAvFAyMDNLgU/bTSKAGlRzTdop2OKaSOaBWI24zzSZ4pHfGc1VmuUQHcwFAywxGC" +
            "KhZsA5NZVzrdtCpO8E+g5rDvfEhbIiXH1NNeQjqzMvPzAmoZLlV6sK4J9ZnJb95gmqc2qykcyN+d" +
            "CXcD0GXUIlzlwD9ay7rWI1kwGGPrXDveM2SWP51Cbgk8k0nbuJXO9sNfMV0rRkcV2b+LWmsLW3nI" +
            "ZUGFI9K8Vt7kq2Qea2BqBKKMngV005JRszGad7nqyyiRNwPXmmk8VyPhrXA4MU7gYxg5rpUnVgcE" +
            "EfWs2XG9ibFAFMD09Tx1qCgAoIpV5pT0oEhB3oHegdMUD3pCuLmgGilAoC42jHFO9aaaLiEHSjtR" +
            "RSAMUlLRQAlFFFAC0mKKKCktBDTDTuabjmgSQ3saTtTucU2goSkJ4pemaTmgBcc0AD1peaMVJYYo" +
            "HtQKco60ANHenAcUuPanBeKAI8UoHNSAdaULQBFigZqXFGO1ADNpFKo55p+OKAMU0IFHNTRg96iU" +
            "c1PHwRimgNXTR82R7V3uhjFqfw/rXC6aOePUV3ui8Wp9zWj+BmS+IuH7p9hXDa82ZXruZOIm+lcH" +
            "rLfvWPucUqOibCpujn5hzVZxyatSjNVX6mobNIrQRDjOTTw3HWoqWpKJgwoDCoaUHFAEoYUoPpUA" +
            "Y80oYilcCwG4ozxUQfg0BvfimBJTc0wHPrSA0ASbqUGq+TQDSuBZ3YpN3B/wqHJPelVjTAlBzSio" +
            "geKcGxQA8cU9ehqJTmnoe1NMB1KOBRRjimA3saZ2pzVGzBQcmgAZuDVWaYLnJ5qve36QK25wAO5N" +
            "cfq/iAtuSBvxoQka+ta2luCsZDOfQ1x93qsszEvIT7ZrMubxnZmLEnOc561nvPkHkfnSvoM0ZLwn" +
            "PJxVWS565PNUmm75qvJKSetTzFKLZdacnJyKiaf3FUTJweajEhyfSlzCaL3ncHPJoWYHvzVDzeD6" +
            "0CQ+tNSuFjTjlIPJ4+lXI7ggY5xWKkxHHb61Mk/H/wBerUiWjbguipyDW3peuS27AFiVHUZ4rkI5" +
            "gR16e9WY5u4P5GqU2KyPVtL1iG7HysQehBrZjcEV4/Z3rxOGVyMY6V6FoOrLdQqCRuAwaFZ6oWx0" +
            "g6ZpQOKhjkyPUVKDmgQooHJoFOUe2KVwAdacBmjFHagbQhHpTcU+kHSkTqNUcHij6U6k+lBQmOtJ" +
            "Tue9J+NBAmKB6UtAxigAx1pmOtP7U00DQ2m+tPppoKG03FSAU0jk0AM6UlO+tNoEmOFGKkwKULUm" +
            "hGFOM09RxTgMCnCgBAM0g96cB60CgAA9KSnYpQvGaAGUuKWgUAJilA55oxSqO1ACqM1PEOeKjUcV" +
            "PCPeqQjW04fMPqK7rSlxag+9cVpifvF/Cu6sBi2Srk/cM18Q+c/uXx6VwWrEeY5+td7d8QP9K4HU" +
            "yPMb606WzFPcxZenSqzjr9asyd/aoGArNmqIqbT8dc9aSpGNpVNIRSipvYA5o5ooAouACgHtTgKN" +
            "vvRcVxAPWnYGDSAEUuTikA0ik6U7HWgAc0DG4pQKMClwPWncBQR2FJ3pwGM80lFwHDpT1NRg05aE" +
            "wJ0ORS1GhpzdKtANdsAmsDVtXjtoXO75hwK1dRkMdtIQecZry3Xb/wAyRlBJANNCQzVdUedmZmJ9" +
            "Kwbi4JzyaZPNycnvVCWTk+tQ5FxVyWWfOcE4qsZCajLdcmoi+B7VNzRRsrlhn468j3qJ5OtQNLz3" +
            "qBnJzz0pIdydnHPNRmT0qIHOTnmlyccCmQ0O3e9OVsdDUfGKTg9aESTrJ61KkmKqL7VKCduDTTsJ" +
            "otpJU8UmOvSs+NutSqxHU5oBKxrQy89a2NI1B7aUMpOO4rmIpfQ88Vetpu5qotolo9e0XUkuoRyM" +
            "8ZrajcGvJtG1JraQFGOMjv1Feh6PqKXkeVPzcd603VydtzZU81KpqCM5FTR96Qbj6KKKLBYKSlpK" +
            "RNhKUUoFGKB2Gn2NNp3Y4pO1BI3NAoxRxQWFIaWkoEhvrSUppKBhimkdTTwaTFADO2KYBTzTWoEi" +
            "f1xSCl4oArPc0AU4UgFOUcGmAKvGacB1oUYz3p1ADcUdqdRQA3bRgY5NOpcUwGduM0qr70tKBQA4" +
            "dKsQDJqAdKs24+b0poRt6Wvzr65ruLUfuEGOwri9JXMgx7V20PES/QVc/hsRHcjvj/o0n0rgdT5l" +
            "YV3monFq+emK4LUT+8b61VP4WJ/EZMmcHNQN0qxL0NQHpWTNEQsDzTcVLTCKl6DG4pV7+lGKVehq" +
            "QQ36Uop22kwfSgdxKVTSYp6jFAWAUmKUUtAhmKQe1PoAoAaOh9KQA9qd6ilUDt2oGkA6c0lOxRik" +
            "GogpaBxRTCw4VL/B1qIU7PFWmIoaof8ARpAemOP1rxzVWKzuOhBr2PVeLaQnsK8c1wg3kx9+KL2A" +
            "w53596qOetWJuScVFsJHHWs2zWMWV2aoXPXk1ZaI8g9fpVd1I/xqeZGnKyEHOc0m4cilI61Eaq9y" +
            "LD9w5xQGxUWcZpFJoIuyRnz3pu4+tNxk9xSgYFCFa49CanB4qBeKkTpz7UxksTAZBqXtxVVTgn0q" +
            "VGOKARLGxHOatxPxwapq3rUsZxwelFwZrWs3PWul8P6k1rcqdx2kgH6VxkT4II9a1rSUjv8AStIN" +
            "oza6HtNlKJYldSMHFXI65rwdM0unjcc7Tj6V0sVU1YmJLSigd6BxSAaaKXikHegBM0dqWigBpppq" +
            "TFNouK1xlKBmlxTaBi7abg0oJxRmgVhuKTHFO/Gj8aAWgwLRilo4oGN7VGRUp5FMIpCWhLijFPAx" +
            "xSYqTQABTx0poGafigAFLRRQAfjRiilFAB60UUUwClFIKcBQA5R2qzbj5qrR9at2/wB6nET0Og0c" +
            "YdfUV2aDAx7CuP0VcyL/AJ712S/yq57ER3ZT1Q4tX/KuEvx85+tdzq522re5xXCXp+dvrTh8DJ3m" +
            "Z0g4OKg7VYfvVc96zNUR0096f3pp75qGMQYxSgUi+1OpAA4paSgCkAcUgpaAKYJBRSjFGKB2Eo6U" +
            "uKQCkKwZoooHfNAxQKPWlpKAv0EHSlFLjAppphcUGlBpo75pQcA8UBYztdkCWMpPZa8Y1Ry00h9T" +
            "XrviaQDTZzn+H/GvGr9zvbGcZpp7jS1KJBYmrcEPy9OarwDMmT0rWgT5RmueqzqpRT3KrW4x0qrL" +
            "a5zgdK2WUCozHkYrn5mjr9mrHOTWxXP+FUZYyCeK6qS3BB9Kzrq0HOBitI1GtzGVI59h1oA7f0q5" +
            "Lb4yOah8sg+1bqRzOnbYiA4pV96kEZP0pyxE8gGi4lG5F/npT05/CpRE3tUiQZ5/rQpWGqbsQYGa" +
            "cnoKupaZXJpVtT26UudAqRVQdqeMjvVo2pA4zTBCRkEVSmmDjYZG2K0bJ/y4qh5RH1q3aHBrRPQx" +
            "lGx6j4Ck/wBFlXsWBFdonU15x4FuSsrR/wB7HFeiwnPNamaVrk6inYpoNKD70hWFpKDSUXAMUCgU" +
            "UgExSYp+KSmAlJjijFBpAHamkdad2pDnFAEZ4pKcelNx1oAKTHFLRQAymEYqQ/SmNQBaIHPNNA9a" +
            "UE0mfeoRSHgDtSimp3zS0gFpM0v40U7hcM0uTSAUuKLjFBo4ptFMBwpRTKcvSmmA9O9XLfrVROnS" +
            "rlt94elCEzptAX96vrxXWp3rlvD3+tX6iuoXvWk9iIdTO1s/6Jj1rh7zlj9a7bXjttgK4e7PzsPe" +
            "qj8AlrJlB+hqA8VOxODxUJ71iajPWkp3SmnvUNBbQQdKUe9IKcKB2ExQKWigYYoHelNJSFsLjigU" +
            "v40lACUgFLRmmCF9aTHpQDThSGNANA7078KO1BKEGaMULxS9uKBoZTGOAcU+mOODRcZzvi5j/Zco" +
            "HTGK8jvM7mAr1zxau7TJABx1ryW7+82aaelyrakNsPm6VqRH5cisyHhver8R+XGa5p9TrotJlksK" +
            "RSM81FmgN61gztRIwGTVeZAQalzSHmpvYNLGVLBycCqrWp5wK2ygOTx+VN8kc9PypqbWxk6dzEFu" +
            "R2NTR25rUEKjsKcIVxwB+VVzuweyM5LX1qZbYdhV9YxjoPypdg9qObzBU11KscGBg08QY6VaVeOl" +
            "Kq9cinFg6aK4g4PeojbjJ4rRCjFN8sc8CtEzKUVsZc0QXJxUEXD9elX7kcc1QT7341tTXU5Kqsdb" +
            "4NcjUEAPWvUoD8oxXlHg/wD5CUeM8V6xD0GK6b6HN1LCn1pwPFMXpThQIdTaOaSi4BQPeiikAtJS" +
            "0etACUdqSjtQAUHpSc5NIc4oAZ2pOOc0vSkNIApKKKYJBimmndjjrTR05oAlpR05oFFZlCrTqQDG" +
            "eaWgAFHFFFAABxxRigUtACUUUVQwp6nimCnjpQgHRjrV61HNUo+9XrXrVLQR1fh0fvR6HFdMveuc" +
            "8OAGQe1dGOAaqfYiF9bmN4hbEQHfrXFXX3j612PiNsAD2rjbo5Zqr7CQo6yZSfoai9amk4BqGsjU" +
            "b24pv8qXPakxUAA9qWkApaB6i0UUVIBijNFA6Uw2ClHSm04c0AmHrTRTu3FABpANApwo7U3NMY6j" +
            "FGaAaNRWsFAzRSdqBIDTG9Kd601uhpFGL4gj83T5k77f8a8hvFxIwPXNe03yB4XB9MV4/rUXl38y" +
            "Y6NTVikZcQ5NXY8gVBFGSe+KsquBXPNHVRDqMimjPqakFKB1rnZ2IYD2/nS9qkCAjvSiP8qm5SuR" +
            "CipGjwD1qPB96Ex2HDpSj07Ug4zSgUJiHD2pQaaOacq84waasxADwacppApxSqpBOauNkS2SKPWh" +
            "gAOKQcClPQ1cdTGTM28POM1TT71XLqq0S4ckda6KZx1d2dL4RXOoRD1OK9YgHArzPwTAWvVYgkAV" +
            "6bCOPwrp1sc3UmWigdKBxSGFFLmkoJCiiigAoopOKACgUUn1o2AKSkP16UZ4pDG+tJxQe9JQAYo5" +
            "opOaEIKQ9PpTs8+1IehpXHYlQdc/rSnPamg04Z7VIxR0560DJpe1JmmrALiiijPHNIBRQKKKAClp" +
            "KBQAUD2ooFNAiWOr1p1H1qlH04q9Z8fnVIGdf4bHz59BXQ9qwPDQ5bPZa3+1XMmGzOd8Rn5yCew/" +
            "CuRuT8zYrqPEbZmYZ6Vyk55P51T+EmOrZWk71CO9SvjnNRDpWLNRtHal7UmKkaAdKKKWgYUooAoq" +
            "RBijHFAzzQOlMLDaKdikIphcUHFAIppoXOKQxaSlA4pQcUCsFA4paKQDevtSmijrTCw31ppHBp9N" +
            "boaQIz751SNi5GMV5Z4kVH1J2j6NXpPiLiwkbPTmvM9VP+k/Wp2ZtBKzKyQhU4pNvrUxdVjJ7Cs2" +
            "4uyM9qxld3OmDUdy2MdyKUEdqx/tZz/jT47w98msnBmyqo2EIFSA5rOhulY9anSUYyDWdmaxmnsW" +
            "8DnOKiaPHSmrLjuKXzOKRdw2elKq/wCRTN9OV8UEkiqBmpAnqKhWQc09ZPf86pJgSBBSFeD60gkG" +
            "M5GKBMvtTRF+gAdqae9L5in0pvrWsDGVilcrnPWoYlywxir86goT3qGxiMlwqDuRXRT1OKqjvfAt" +
            "pttzIRznHNdnEOtZOg2otrNE9hmtiMYFdF7nOtB4pRQKSkmMKXNHSgUyUhKKKO1AB2pM0djR9KAE" +
            "ooxSUhiGkpc8EUlADeaT8adjrSUDQgHX2ozilFIfpQFgFIRwaWkIODigNloT4FKPage1N5BPPFQA" +
            "vbilGec02jPvQAoanZqOlUkA0APB9ab3pR0zRQCFopOaOcUAL9DQO9IKBQgRLHV+0PPP4Vnx9avW" +
            "v3h9aqIdDtfDP8Zz2/rW92rA8MciT6D+dbxPyk+1XLcmOiZyPiJ83Dge9czMc5z3rd1mQtK5z61z" +
            "8p5qp7ChrcgfvTKe1RkVk9jQMZoGfrQDRk1Aai0UgpMUBccBRQOB1oHSgaWuoo96ac06mn60kMXJ" +
            "xRng0lFUK4UUooxUgKOKBSGgUAKOmKKWkoATtRQMUUAJTW6GlJ9Ka3SgZh+JlJ0yTb2I/LNcLrtm" +
            "sdjBOCd7kA/rXoWtc2E/+6a8x1i4kZwhJKqBgVDv0NoNKLTM587SOazrhDzWkZFPy559MVSucYNS" +
            "9DVJNGY6GouR0qw4+YnNQOcGktR2QscrqamW7ccGqZPvTlJ70uXuClYvpeHPUmrMdzkdeazI1HUn" +
            "9alQ89TUSiaKTNNZuvIpHuMA81UGcVFICeBnFQo6lc7J/tpBP+NPGoYH/wBes4rjJLfpUR9s1qoI" +
            "zc33NX7fu7n86QXhGcE59M1ljp1qWNCR1q1FdDNyZordse5z9au29ySMMR2rJRD1qzECBTSWxDkz" +
            "XZgYz0qbQJI49RR5egGRWernYQat6XbSSyFo1J21cbJkTbkj1DSr+OchVyD2BGM1txniuJ8LMzXv" +
            "I4Rf16V2kfpWq2MmrMlFFA6UUEhSdOtKD60lAWFFJjrR0o7UwQUlLSUITG0djS0lADeaWlPFJkc9" +
            "KBpCU0mnGm4oAO1JQKXikCExxRijFGKYEobNITTQwpQetQAUq03ilFAC0KaKTigCQHilFMXocHmj" +
            "mgB+aTPFJketITwQKAHD9aUUxTjrmnA5oAevWr9r196z1q/an5sE1UQO18LcrKe2B/Wtuc7beQ+i" +
            "msXwoAYpz9K1747bSb/dq3qxbK5wmpvl2Hesdzz9a09Rb94Tnmsp+pNOb1FBaETHJ4plLSVkywpB" +
            "3BFOxQB1pAIKcOM02nDvSGmJSjpRijFAaCYoxSijFAxwxik+tN7UgosK4opQaQD3pcUAhaKKTtSs" +
            "O4CigUGmLYaOtO9aTFAx2oGJSNwDSmmtjFSBl60M2E4/2a83vYVLA4Gc16ZqK7raQHutedagmGGD" +
            "7VnO/c6aGzMeeMDPAqhOmQRWvKvFUZY+tRc2SMh4vmOTUE8fy8dq0Z0JzgVSkzg0lK+xNu5nFSDi" +
            "n44GTxUjLzTMZrRO6I5By1bs4TK4UfjVeJK3tItN3JzWc5JI2hG+hL9gj8nI64rJuYzGzcnjiuxW" +
            "yUxgEHOKwtWtChY4zjpWNOprY1qUrR0OekY8ioWOAasTKQTn+VQ4BBzXUu5yNdxkb89Kuwe47VVi" +
            "j+bOM1rwKvlgADgelURqNjU9R0qzFHuNMiibPHTNXoIyBS0BJjdgC/St3w5cpFHKgQlmGc1ivCQv" +
            "U4q7o+Q7DnnFVFKRLbWyO18LQbTLLjr8orqIj/8ArrM06EQ26qo7c1ox/drVdjGTu7snVhRu60wc" +
            "UtUIXdSZIzSUUALuxRu680z1o7UhEinGc04ciolPHWnK1ADyRTS1Jmm5HrTBMVs0i0AjBpM+9AWH" +
            "GmE0v402kDFBzRSdBQDnNAAKCeDRnrzTCaYJEv4UUi0tZgAxQMUe1JTBD8/lS5ptFACg470buvNJ" +
            "SUhjs9aTd6UlFFwHA/SlU1GOKcpFMViZauWp5B61STkZq1bHkU0CO78InMVx/wAB/rWlrEnl6dLz" +
            "yfl/Wsvwef8AR7j6r/WpvE0220RBnJf9BmrTuyG7I429fMjc96oOeDVm4OWP1qo/NDerZUVoMNAp" +
            "MdaMVmMX1pB3o7UL3pDSuIKUdKSlApjCgUmKMUgsOFBpKDSQwopRn60tO4rCUmaDRigXkAIpc0go" +
            "FAC8UtFFIYUynUlACUw9KfTG6YFAynejNvJ64OP1rznU1IK16VMu5GHXtXn2uwmOQrjkNUNG1KVr" +
            "oxnHBzVeVcg4qzJ0NVXPJrCfkddPYpyp1qjLGOfStKTODiq7DIIrNNobiZjpgHikjgz6/lWisWe1" +
            "SLFgZ6U+ewKLZBbW4HXmug0xcYAHSs23jw1bVimCPc1nJ33N6cbGmq4SsrVYwyHpxxW0MBDWbeLu" +
            "DDtUQ01Nam1jkL235yBWc0Xp1rp5YsgjArOmtcE4FdqnoefKJmxJg1oWy5FMSHBPFXLePHWncmxP" +
            "AoA4FWoU4qOFc8Yq5Gh2nGOKW7BqyIJFG3HbitPQLTfdR57npVHGZFHYkV0nh2D/AEncOijitYXM" +
            "paJnWRDC4FWE5FVoelTofetkjmJh70Z4poNL+NMB2cUmaTtSUAKDRSA0meDQAoPWlGKj3dhQDwc0" +
            "APzyRg4pM9eaZn3pM0APz70AnuaZmkzQBJu460m6mZozQA7d70m7rzTM8UgpAPz15pM9aSimBYHe" +
            "iilWoEA6UUlApXCwtGaUd6M8dKB6jaQd6XtRSABS0gFLjFABQo60gwKcM00BKnSrNscGqqdKs24+" +
            "bGe9NCR3fg9sWk/puX+VJ4rI8qAjrlhipPCEf+gSHjl/5VW8UptWL1Oa0ijKWrOQlPJ9uKqv1qzL" +
            "1P8AOqx71L0NVsMxSjpRRipuMKKXFJigQAcUAUUopDuwooxRigLiCjGaWlXvRcdgGMYpKdSetAWE" +
            "oxRRQITqKUe9BFIAe9AbC4oowcUAHNAB60uKTBoHvSGJimEcVIaa3Q0IZC44P5Vy/i61BsWkA5Uj" +
            "J79a6oisjxDD5umXAUc7c8+1FhxZ5xKPlOapuOTjNX5cbfbFUX5zXNU3O2k1YiK5pixVMB1zSc9q" +
            "xbOpIZtAHvSIu5uaGJORTd/lgmktx3L1vGozWjb/AC9PSsKK8UtjPNaNvc8cUpJlxkuhtB/lOarT" +
            "DIPeoUuBjqDSPcJg5IqYxYSkrFOZSCc9PWqzIGz71cndWjO3HSqYOCeK6I6HPK25C1uRnHSiJSKt" +
            "LyKUL6impE2QsPXFWos7TUEa4NW4l+WrRlLRDI0JuFxngg5/Ouw0CLbG7Y6kCuc0y3ae7CIAcDJ9" +
            "q7SygEMQUduv1reCOSct0Wo+BUq+1MWpVXjJrRGY5enNLSAUoFMAope1H40ANpp6U8DrTcUANo/G" +
            "lx1NIBQAmaSlxikxQJBQB1oGaWgYyk7080mKVgEooopgNpASadxikI4pAWfpTgKQd6WoQgxSYpw4" +
            "NLigBoo7U4UtFguIKBjmlopgN45pKdijAoAbTl460DFAHpQBIvSrdoOQaqL3q3a9aaA9D8KrjSl9" +
            "3Y/rVDxe2PJHsTWp4cG3SID65/mayfGLfvIhx9w/zq09SLKxx8vU1XbvU8nU81A/cVDL6DKUUlFI" +
            "Be1JS8UYoAOKSilxQAmTQBzQAR3pRwKQ1YBThTR0pw4oGgxQRTjjHFMHehMTG4p1FFIaEpVHrSYy" +
            "KdTBeYUUY4paQCUmKXtSDpTEmFJjikNJRYdxMZzmql5HvgkT1GPzzV3GRUMi8H3oBPseU3kZikkQ" +
            "/wAJx+tUCOuK6HxXb+RqTkA4cbq55jy1YVEddGV1YiPegD0pCeuajknWPJJrnsdsWktSYR9STVO6" +
            "6EdKY16MELiqc9wWB5PTpSjFkSqK1iEybHyM8GtC0uwRjOCPesZ2JJ9KashU8GtuS6sZKVtjphdg" +
            "L1qvLcOTw3GayFnYnrUqSE9TkURjYUpto27ZywwSalKke9UbK4A61pxujr1FEm+g4u4iHjmpFFJt" +
            "7jpSxj1qVZlNk0Q5q1CKrxDGatRjitIroYzdkbfhiLM0z+mBXToMCsnw7B5dmD3c5NbKiupKyscT" +
            "d2KoqVRTVU08cVQCgUD0opPagBe1JQPrRSuAe1FFLTAYT1FA6c0YpKQDSOoFJT8cU0qaAEo5pQuK" +
            "XHFK4DTQaMUhpgIaSngcGm4pISGiloopjLPagUppAMVmA4UDoeaSkFUIUdMUoNNoqRknbrSfjTRm" +
            "iqELRSUZqdAAYpwOKaKUUFdCZeas2vUGqq1Ztj0+tWtSeh6BoE8S6fArzHcq8rg8cnvWR4snjluY" +
            "/JfcBHg9eDmptKU+Qf8ArnWVrJxIc9gK6OVWuYJu9jGk6n61XNTS9zUNc7OgKKSlpCFAoHFA+tIO" +
            "KAHZFHHakxS0AJQtFIKQ0L+VKPek4waB+NAbjvXB4opAetGfei4rBSj2po68UtIpC0U0HHelBxQh" +
            "ig9c0E8UgpadxWCkz70UnWgVrCUDvS0YoAQ5pjfdNPzUTnrQHocn42t91qsy5yhwfx/+vXBSHHFe" +
            "p65GJbGdGHVD1/SvKZiQT6jrWUnob0nYY7AA1kXUuSe/NaMjZU4rJnHzNn14rnS1OpttWIRJk+/S" +
            "n4yKg5UnrxSiVvpWtriS7jzCSDg1A8RGetTK7DJOcU9ZRzkUaoOVMrxo2ehq0iNjkGgOvYDNSJPj" +
            "72DTbYcqSFjJUd+KsQ3TL3/WoSyMCRj86hxzihW6mT0OgtLnzBjIq4pz0rC09iG6mtiNsDINLlS1" +
            "KjLdMtxtjrVu0QyyBV7kCs2NiTjr0rpPDdtvn8xhlVxj61pCJlUkdXZR+VAijoBVxQAKhTpUimtk" +
            "2cxIOKUGmDpS0xj8ig0wGlzQAoPWigUlK4BSd6KKYBik9cUuevrTQeakQvakpN3XBNGeOetO4XDg" +
            "0fjTfpSZ96Yx5FJikFIakQuPSk9aBwKT8aAE7UUtFO4XJtwoBplID1qBkgPXFLmmA46U5TxzQA4G" +
            "lpopaEAuKKM0mTn2oHYUUUgPGKBQAtKKaD60tAiVOlW7UcfSqadKu2gzwPpVxF0PSdGiVLOL5Qfk" +
            "XJx7VyvirA1OYDGAFHp2rsdP4t1X0Cj9K4rxQ27U7n/eAx9K16mcUrGBKetRdqfIeTntUfY4rJmo" +
            "UA9aTcO9H40hAvOadnFNBxQCBSGPzR2pM0lAC5pRTQcUButACj2p1MU5pwpABpKTNG73ouOw4cUd" +
            "etRl6Nw70hrsSClqLd70oahO4WsPoHNMB+lAPoKLhqSDFFIG9aTcKYC0gFGR1ppai4kh3UVE44NO" +
            "3UyRhg9KVwsYev3S29lKXPJGB7k15dd8Ox7V1Xje/wB9z5CN8qnn3rkbzlSetS1obU+pVkcYNU3I" +
            "JINDy9cfzqsZuT0NY8hspaak4QY5qMxc5pFlHrj2pS2ATmkk0aJiqMUjRqc4qM3AHB61GLoZ6CqS" +
            "bHzLZlhIh2p/kjHFQw3K456/Wp1lB5ppNE3Vtx0cBPpSNCymrMMgOelOcrtOSKV2nqZ6CWfB5696" +
            "1I2GKyYXAJxVxJBg9Pzq1qQtEzUs1LzKq9TjFehaPai3tVX+Lv8AWuK8JCOa/wAORlRlfrXoEfCj" +
            "FbLYwm7snUU8e1MFOFO5A4EinBqYKVaAJARilqNTThQAv0ozSdqYe+TQA/I55phPvSdjTc9aAHbj" +
            "SA03NJuoAfmmg4pM+9Jn3poEOzRmjim5NFwuSZptNDce9N3GhAiT8aKjyfWjPpSAkz703PWmUmev" +
            "NAFiimBh3NKGqRkq+9PXHOKhBp6kYNAEmaTn8KQYNHHSi4C59KPrSDFLQAlKOM0maB0NCAcPelFJ" +
            "SigCSPpWjpqgyIDnBIBP1OKzo+BWnpf+uix/fX+dXEXQ9RjRUAC+gH5VwPirjVLnB/iB/SvQK868" +
            "SMG1O6Of48flVLqT5GE55NMJwDTm6nPrTGqWUIPrSg+9MBo4NSCJOPWjj1pnakBPrUgPzSA88nim" +
            "ZOPejtSQx+eaXIFR0ZGKBokB4o3DB5qHeB1NRNOqg5I496lyGkWi9M3E/wD66z5L5ACA2T6VVk1N" +
            "QDgc+tQ6iRaps2S/HWm7xXPSao2Dg8fSoG1GQ5wx/lUe0sWqT6nTmYDPT86T7SmOSB+Ncmb1znLH" +
            "86YbonuTS9qWqSOtN0n94fiaVbyMfxjj3rkRck9yacs/uaSqh7JHWi5jPRhSidezCuSE59acLg9m" +
            "OaarMXsV0OsEme9KJM9DXKreSKSd5H45qaPUZV6NVxqXJdJo6XcPWqV/ciGFmJ57VnpquP8AWAfh" +
            "Wfql4Z+BkKP51rG7M9jjfEcxe83Z96zZGyh57Va1zH2rmqLHKmnN6GlLW5l3AClhz1qk7YNaN0vW" +
            "suUYJ9qzg0y5K2w5ZPWplf5TzVIN19KcH7A8/SqcRKViSYHnFVi3Wps0wr1x1oSsK/UIMk9avQk9" +
            "+9Uovl5/OrSvgZFMcX3LaOQDg01pjz1IqBZeo4NJnOe9Sl3FzFqGTnNXInycVmwnPGK07ZdqkmqV" +
            "iX2NLSbo218jqTwRn3r1KwnE8COvQjNeNK2JywPevSfB90ZLVkYkkYNbJe6YyVnY6hW9acD6Golb" +
            "jjpTgaRJID60Z9KapyKUfWhAOBpwPpTKcKAHA+9NopMUAIfamk8GnGmN04oACeKbmjtzTaAHZpKB" +
            "xRQAuaM0lJQAppKKKACijFH40ABzSetL+FIc44oAasitnmpFb3ribHVpFbG4nnuetdLYXizoSp5H" +
            "UVHmBqr04p4NV0bipVPFCGSD2pRk01aeooEKnQjvTsU1e9Op2GIxpRz1pMDNL9KAFFHrSU5elNAS" +
            "R9K1tGBN3D7yLj86yoxWzoS5vrb3kH86uC1JlsekJKp4ArzfW23X9yeuXP8AOvQIM7l69e9eeakd" +
            "9xKw5yx/ma1skmYwk5GWc85NMYVKw5NMK1gzcixzSqDipNlAWgCEjGaTPWpXU4NQPwDUsYucU0uO" +
            "1RM4Ucmqk94q5APNZuSRSTbLrS8HJAqrLeImQDk/Wsm4vc5y2BWfLdk5xmsZVehrGHVmvPqB5w2B" +
            "WfPeFsgH9aznnz1qBpSc81g5m8YovNcsc8k1E05bvVIyn1pN9Rzdi7FsynPWm+ZVXeMVXuroRKef" +
            "m7ChXlsF0ldl/wA7GckfnTRcJn7wrnHnaQksT9M0iOSa6FQdtTH266HUJJ6GpUfjNc/aPKT8hJFa" +
            "sBkOPMAH0OaToNdRxrRe5d38cU5Cc96iU4FOL8deaqNFvcbqIl3Yzk0xpcA4I+tRM/Byc1VlmAQ8" +
            "/rW0KSWxlKo2TNc/PtU5yeae8ny5zWZauHkJ98CrbNhea2Wmhl1MHXf9YD2zWarjB5Fa+rIGjOcc" +
            "d6wd2DWc1pdGlOyYs3IPesy5TmtJjxVOVcjmsYtm7s1qZjAj1pinB96tOnXvUDRnGRWyfcxcWhc5" +
            "pAxGRmo8Ecc0nNMm5Juxz3qRWJHpVcHFSLz0ppIGyYEjmpY8ngZqJEJ61etosDP40r2Bak1pEQea" +
            "vg7VqCLAzSu/B61GrZVrCxKWl9ya7PQpzaINo9M1yelxb5Cx6V0tt8o/CuqC9055PW519pqCSDrg" +
            "+hrQSRWHWuMhfbkZq5bXzxNwxI9M0nHsJHVhqkTvWPZ6iknDcGtWKQEcEGpsBNQBQpBFFACimmlp" +
            "DSBB+FNPQ0/tTSPWmBHRTiKQUAJikxx1pwPHIoAzQAg96AOtPC+9IFJoAbikp/QGm+9IBtGKU0lM" +
            "AGe9NPSnfjTaAPKIZircE9q3tNvTG6sp+tcqjnPWtOykOOOaAPRrG5E0QZener0Z4xXIaHe+XIEJ" +
            "4b3711UT5FQ9ALI5p6dKjTpUi+1MY9c05fegL1ooSsAg96WiigAApwFA705RTEPjFbnh8f8AEwt/" +
            "9/8AxrFjFb3h1R9vhJPAJz+RrSCM5vRnXzyulq7IC0gHGBnrXE6hBJGcNG4zzyp55613lsRkc1je" +
            "KwDcRDt5f9TWz7HPT2epxvlOx4Vs/Q08274+435Gt3R4g18vfAJ5rduohiHP/PRe31qeVbGvOzgk" +
            "gYkjafyNSfZ2A5U/lXZ6VGBqWpsMfeRentWxJsC9F/KoejsUpPc8quUKA5GKx7i5Kkjg4r0HxV4h" +
            "t7RGitliluM5JwCq9fzPtXlWpXzSyvJI252JJPTmuerJJaG9OLe4txdE5yeKy7i7xnBwe5qvdXR5" +
            "ANZj3GcnNcjk2dKSSLc1wWzmq3nYznp61WMx5pnmdeazsUkWmkB71GXPbpVcv6Um/rU2KRYDgjn8" +
            "6UMKrqfepE56DmmohzaCySBEJz0FZMzGRySfpWq9s8oA6L606GwjQ7myx9TXTRgoq7MKk7uxkwW8" +
            "kh+UH61oW9gF5kJY+lXgFXpjikLgd66L6WRlZbixoqjAAAqUHFQLJ6YNIZG9sUkrjTsXA57nikMm" +
            "cgGqXnMSaeh4J9apLQG2Ss3B9ao3EnykGp2fAOaoXBJ4qkTfsWbE/LnnBNWHbOfSq1qMIAD2qV26" +
            "ikNWsVbob0Oa5u7QxysPyrp25BrE1aHjd6UWuOLsZobPHpTScg1EWx7UBvpXO42Z0xeg1hULCrB6" +
            "dqhbGCCaQ9yErxzTDHmpRyfalqk2iLEIjHvUscQHNOUDNSL7VXM7E8pJGAf6VPHxyOo6VVDEHg1N" +
            "G3HWpKtYso/HNKuWbFVg3+c1fsYyecHNaQjczk7I1NOj2jitaM/LVG1AAxV0EAV0owLCNkHJpvm4" +
            "OO1Rq2FOTUDPycGkCNOCbByCa17S7ZBkGuagk56+1aMcgC0hnU2l6j8E4NX1kBri47nB4Jq9b6hI" +
            "gxuyPepa7COn3ZpeKyrbUY34Y4P1q2s6sOGH51IFrNNz1xUQbPQ0uaYC560CkopIBf5Uo9qSlHFM" +
            "BVPBzThTU+vFOpIApD0NKKKYEeKaRUuODimlaAGYpMcmngUEDFAH/9k=";

    private JFrame frame;
    private JLabel imageLabel;

    private void initGUI() {
        frame = new JFrame("Video Playback Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);


    }


    public static void main(String[] args){

        Main app = new Main();
        //ImageDownLoader imageDownLoader = new ImageDownLoader();
        //imageDownLoader.downloadImages("banana" , true);
        try {

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(base64String.split(",")[1]);
            System.out.println("Bytes length --------- "+decodedBytes.length);
            File file = new File(FileSystems.getDefault().getPath("VisionI/positive_images/banana.jpg").toAbsolutePath().toString());
            byte[] bytesArray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            System.out.println(app.detectImage(decodedBytes));
            //app.bufferVideo();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private Hashtable<String, CascadeClassifier> getCascadeClassifier(){
        String path = FileSystems.getDefault().getPath("VisionI/trained_classifier").toAbsolutePath().toString();
        File folder = null;
        folder = new File(path);
        final Hashtable<String, CascadeClassifier> map = new Hashtable<>();
        Arrays.stream(folder.listFiles()).forEach(file -> {
            if(file.isFile()){
                String name = file.getName().split("_")[0];
                System.out.println("Name -------"+name + " " + file.getName());
                map.put(name, new CascadeClassifier(file.getAbsolutePath()));
            }
        });

        return map;
    }

    private String detectObject(Mat image ,  CascadeClassifier detector, String name){
        MatOfRect detections = new MatOfRect();
        detector.detectMultiScale(image, detections);

        System.out.println(String.format("Detected %s ",
                detections.toArray().length));

        // Draw a bounding box around each face.
        for (Rect rect : detections.toArray()) {
            System.out.println(rect.x + rect.y);

        }
        if(!detections.empty() && detections.toArray().length > 1){
            return name;
        }
        return null;
    }

    private List<String> detectImage(byte[] pixels) throws IOException{
        OpenCV.loadShared();
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(pixels));
        Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, ((DataBufferByte) img.getRaster().getDataBuffer()).getData());
        Hashtable<String, CascadeClassifier> detectors = getCascadeClassifier();
        List<String> names = new ArrayList<>();
        detectors.forEach((key, cascadeClassifier) -> {
            System.out.println("Key -------" + key);
            String name = detectObject(mat, cascadeClassifier , key);
            if(name !=null){
                names.add(name);
            }
        });
        return names;
    }


  /*  private void bufferVideo() throws  Exception{

        OpenCV.loadShared();
        Mat webcamMatImage = Mat.eye(3 , 3 , CvType.CV_8UC3);
        VideoCapture capture = new VideoCapture(0);
        Image tempImage;
        org.opencv.core.Point point1 = new Point() , point2 = new Point();
        initGUI();
        if(!capture.isOpened()){
            System.err.println("Error");
        }
        if( capture.isOpened()){
            ImageProcessor imageProcessor = new ImageProcessor();
            while (true){
                capture.read(webcamMatImage);
                if( !webcamMatImage.empty() ){
                    tempImage= imageProcessor.toBufferedImage(webcamMatImage);
                    ImageIcon imageIcon = new ImageIcon(tempImage, "Detection");
                    imageLabel.setIcon(imageIcon);
                    frame.pack();  //this will resize the window to fit the image
                    Thread.sleep(50);
                }
                else{
                    System.out.println(" Frame not captured or video has finished");
                    break;
                }
            }
        }
        else{
            System.out.println("Couldn't open video file.");
        }

    }*/

}
