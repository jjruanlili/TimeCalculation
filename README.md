# TimeCalculation
某面试公司的一道机考题，计算非重复工作时间段。
现在有一批作业，每个作业分别有开始时间和结束时间，作业之间的执
行时间范围可能重合，也可能不重合，请编写一段程序求出这批作业的
实际占用总时间（重合部分不要重复计算，不重合部分要相加）。
任务     开始时间   结束时间
任务1    begin1    end1
任务2    begin2    end2
如果实际工作中，取数据从数据库中取，则可在sql中就可实现取出的数
据按时间大小排序。
这题目中是给定一个表格，当时做的时候思路就有点跑偏了。想着一个一
个分析数据，依次对比，这样会导致情况比较复杂，可能数据会出现多次
断层然后重合。
后来突然开窍了，如果不管三七二十一先排好序，那情况就非常简单了，
只会是重合情况和断层情况，出现一次断层，则储存已重合的部分，继续
往下重合，直到结束。
