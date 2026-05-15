SEEDER = "/Users/likhithr/Jan-Aushadhi Finder/app/src/main/java/com/janaushadhi/finder/data/seeder/DatabaseSeeder.kt"
with open(SEEDER, 'r') as f:
    lines = f.readlines()

anchor = '        dao.insertAll(analgesics)\n'
insert_idx = next(i for i, l in enumerate(lines) if l.strip() == 'dao.insertAll(analgesics)')
