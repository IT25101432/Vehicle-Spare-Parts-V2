import os
import re

directory = r'e:\SE\backend\src\main\java'
lombok_annotations = [
    '@Data', '@Builder', '@Getter', '@Setter', 
    '@NoArgsConstructor', '@AllArgsConstructor', 
    '@RequiredArgsConstructor', '@Slf4j', '@Builder.Default'
]

for root, dirs, files in os.walk(directory):
    for file in files:
        if file.endswith('.java'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            if 'lombok' not in content and not any(ann in content for ann in lombok_annotations):
                continue
                
            lines = content.split('\n')
            new_lines = []
            has_required_args = False
            has_slf4j = False
            class_name = file[:-5]
            final_fields = []
            
            for line in lines:
                if line.strip().startswith('import lombok.'):
                    continue
                
                # Check for annotations
                is_annotation = False
                for ann in lombok_annotations:
                    if ann in line:
                        if ann == '@RequiredArgsConstructor':
                            has_required_args = True
                        if ann == '@Slf4j':
                            has_slf4j = True
                        line = line.replace(ann, '')
                        if line.strip() == '':
                            is_annotation = True
                            break
                if is_annotation:
                    continue
                    
                # Collect final fields for RequiredArgsConstructor
                if has_required_args and 'private final ' in line and not line.strip().startswith('//'):
                    # rudimentary extraction
                    match = re.search(r'private\s+final\s+([A-Za-z0-9_<>,]+)\s+([A-Za-z0-9_]+)\s*;', line)
                    if match:
                        final_fields.append((match.group(1), match.group(2)))
                
                new_lines.append(line)
            
            # Insert log and constructor after class definition
            if has_slf4j or has_required_args:
                final_output = []
                class_def_found = False
                for line in new_lines:
                    final_output.append(line)
                    if not class_def_found and 'class ' + class_name in line and '{' in line:
                        class_def_found = True
                        if has_slf4j:
                            final_output.append(f'    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger({class_name}.class);')
                        
                        if has_required_args and final_fields:
                            args = ', '.join([f'{t} {n}' for t, n in final_fields])
                            assignments = '\n'.join([f'        this.{n} = {n};' for t, n in final_fields])
                            constructor = f'\n    public {class_name}({args}) {{\n{assignments}\n    }}\n'
                            final_output.append(constructor)
                new_lines = final_output
                
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write('\n'.join(new_lines))
            print(f"Processed {filepath}")
